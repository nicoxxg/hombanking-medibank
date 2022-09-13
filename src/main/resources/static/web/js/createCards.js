const { createApp } = Vue

createApp({
    data() {
    return {
        
        type:"",

        color:"",

        card:[],

        datos:[],

        error:false,

        errorRequest:false,

        messageError:"",

    }
    },
    created(){
        this.crearCuentas()
    },
    methods:{
        crearCuentas(){
            axios.get(`/api/clients/current`)
            .then((response) => {

                console.log(response)
                this.loans = response.data.loans
                console.log(this.loans)
                this.datos = response.data
                console.log(this.datos)
                this.cards = response.data.cards
                console.log(this.cards)

            })
        },
        createCards(){
            console.log(this.type)
            console.log(this.color)
            
            axios.post("/api/clients/current/cards", `type=${this.type}&color=${this.color}`)
            .then((response) => window.location.href = "/web/card.html")
            .catch((err) => {
                if (err.response.data == "you already have 3 cards of that type created") {
                    this.error = true
                    this.messageError = err.response.data
                }
                if (err.response.data == 'you cannot create more than one card of type "TITANIUM"') {
                    this.error = true
                    this.messageError = err.response.data
                }
                if (err.response.data.error == "Bad Request") {
                    this.errorRequest = true
                    
                }
                
            })
        }
        
    }
}).mount('#app')

