const { createApp } = Vue

createApp({
    data() {
    return {
        data: [],
        accounts: [],
        numberOrigin: "",
        numberDestiny: "" ,
        numberDestinyManual: "VIN"+"",
        amount: 0,
        description: "",
        error: "",
        datos: [],
    }
    },
    created(){
        this.obtenerCuentas()
        this.getClient()
    },
    methods:{
        
        
            obtenerCuentas(){

            axios.get(`/api/clients/current`)
            .then((response) => {
                this.data = response.data
                console.log(this.data)
                this.accounts = this.data.accounts
                console.log(this.accounts)
                
            })
        },
        createTransactionsExternal(){
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })

            swal.fire({
                icon: 'warning',
                title: 'Are you sure to make this transaction?',
                showDenyButton: true,
                confirmButtonText: 'Save',
                denyButtonText: `Don't save`,
            }).then((result) => {
                if(result.isConfirmed){
                    axios.post(`/api/transactions` ,`amount=${this.amount}&description=${this.description}&numberOrigin=${this.numberOrigin}&numberDestiny=${this.numberDestinyManual}` )
                .then((response) => {
                    swal.fire({
                        icon: 'success',
                        title: 'your transaction was succesfull',
                        showConfirmButton: false,
                        timer: 1500,
                        timerProgressBar: true,
                    }).then((result) => {
                        window.location.href = '/web/accounts.html'
                    })
                }).catch((err) =>{
                    this.error = err.response.data
                    console.log(err.response.data)
                    if (this.error == "Missing data") {
                        Toast.fire({
                            icon: 'error',
                            title: 'Please verify that you have placed all the information correctly'
                        })
                    }
                    if(this.error == "the account you are entering is equal to destination account"){
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "the account entered does not exist"){
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "this account does not belong to you"){
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "The account you want to spend money does not exist"){
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "you don't have the money to spend"){
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }

                })
                }
                
            })
            
            
        },
        createTransactions(){
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            })

            swal.fire({
                icon: 'warning',
                title: 'Are you sure to make this transaction?',
                showDenyButton: true,
                confirmButtonText: 'Save',
                denyButtonText: `Don't save`,
            }).then((result) => {
                if(result.isConfirmed){
                    axios.post(`/api/transactions` ,`amount=${this.amount}&description=${this.description}&numberOrigin=${this.numberOrigin}&numberDestiny=${this.numberDestiny}` )
                .then((response) => {
                    swal.fire({
                        icon: 'success',
                        title: 'your transaction was succesfull',
                        showConfirmButton: false,
                        timer: 1500,
                        timerProgressBar: true,
                    }).then((result) => {
                        window.location.href = '/web/accounts.html'
                    })
                }).catch((err) =>{
                    this.error = err.response.data
                    console.log(err.response.data)
                    if (this.error == "Missing data") {
                        Toast.fire({
                            icon: 'error',
                            title: 'Please verify that you have placed all the information correctly'
                        })
                    }
                    if(this.error == "the account you are entering is equal to destination account"){
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "the account entered does not exist"){
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "this account does not belong to you"){
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "The account you want to spend money does not exist"){
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "you don't have the money to spend"){
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }

                })
                }
                
            })
        },
        getClient(){
            axios.get("/api/clients/current")
            .then(response => {
                this.datos = response.data;
                console.log(this.datos);
            })
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