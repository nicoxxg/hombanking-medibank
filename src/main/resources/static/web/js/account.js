const { createApp } = Vue

createApp({
    data() {
    return {
        transactions: [],
        lengthTransactions: 0,
        datos:[],
        clientId: [],
        account: [],
    }
    },
    created(){
        this.crearCuentas()
        
    },
    methods:{
        
        crearCuentas(){
            console.log(window)
            var idCuenta = location.search.split("?id=").join("")
            axios.get(`/api/clients/current`)
            .then((response) => {
                let accounts =response.data.accounts
                console.log(accounts)
                console.log(idCuenta)
                this.account = accounts.filter((account) => account.id == idCuenta)[0]
                console.log(this.account)
                this.transactions = this.account.transactions.sort((a,b) =>  b.id - a.id)
                this.lengthTransactions = this.transactions.length
                this.datos = response.data
                
                
                
            })
        },
        eliminar(id){
            axios.delete(`/rest/transactions/${id}`)
            .then((response) => console.log(response))
            .then(() => this.crearCuentas())
            .then(() => this.crearCuentas())
        },
        logOut(){
            axios.post('/api/logout')
            .then((response) => {
                window.location.href = "/index.html"
            })
        }
        
        
        }
        
    }
).mount('#app')