const { createApp } = Vue

createApp({
    data() {
    return {
        
        datos:[],
        
        loans:[],

        cards:[],

        fromDate:"",

        thruDate:0,
        


        
    }
    },
    created(){
        this.crearCuentas()
        
        this.seeCards()
    },
    methods:{
        deleteCard(id){
            console.log(id)
            
            axios.patch(`/api/clients/current/cards/${id}`)
            .then((response) => {
                console.log("created")
                window.location.reload()
            })
        },
        seeCards(){
            axios.get(`/api/clients/current/cards`)
            .then((response) => {
                this.cards = response.data
                console.log(this.cards)
            })
        },
        crearCuentas(){
            axios.get(`/api/clients/current`)
            .then((response) => {

                console.log(response)
                this.loans = response.data.loans
                console.log(this.loans)
                this.datos = response.data
                console.log(this.datos)
                this.date = new Date().getFullYear() //new Date("Jul 12 2028")
                console.log(this.date)
                console.log(this.thruDate)

            })
        },
        logOut(){
            axios.post('/api/logout')
            .then((response) => {
                window.location.href = "/index.html"
            })
        },
        fromDate(date){
            let fecha = new Date(date)
            console.log(fecha.getFullYear())
            return fecha.getFullYear()
        },
        thruDate(date){
            let fecha = new Date(date)
            console.log(fecha.getFullYear())
            return fecha.getFullYear()
            
        },
        
        },
        
        
        
    }
).mount('#app')