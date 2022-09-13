const { createApp } = Vue
createApp({
    data() {
    return {
        accounts: [],
        datos: [],
        cvv: "",
        num1: "",
        num2: "",
        num3: "",
        num4: "",
        numCard: "" ,
        amount: 0,
        numberAccount: "",
        error: "",
        description: "",
    }
    },
    created(){
        this.getAccounts()
        this.getClient()
    },
    methods:{
        addPostnet(){
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
            console.log(this.num1 + " " + this.num2 + " " + this.num3 + " " + this.num4)
            axios.post("/api/postnet",{cardNumber:this.num1 + "-" + this.num2 + "-" + this.num3 + "-" + this.num4,cvv:parseInt(this.cvv),accountNumber: this.numberAccount,amount:this.amount,description:this.description} )
            .then(response => {
                console.log("created")
                window.location.href = "/web/accounts.html"
            }).catch((err) =>{
                this.error = err.response.data
                console.log(err.response.data)
                if (this.error == "information is missing") {
                    Toast.fire({
                        icon: 'error',
                        title: 'Please verify that you have placed all the information correctly'
                    })
                }
                if(this.error == "the account does not exist"){
                    Toast.fire({
                        icon: 'error',
                        title: this.error
                    })
                }
                if(this.error == "the card does not exist"){
                    Toast.fire({
                        icon: 'error',
                        title: this.error
                    })
                }
                if(this.error == "the account does not belong to the authenticated client"){
                    Toast.fire({
                        icon: 'error',
                        title: this.error
                    })
                }
                if(this.error == "the card does not belong to the authenticated client"){
                    Toast.fire({
                        icon: 'error',
                        title: this.error
                    })
                }
                if(this.error == "the selected card is disabled and/or eliminated"){
                    Toast.fire({
                        icon: 'error',
                        title: this.error
                    })
                }
                if(this.error == "the card is expired"){
                    Toast.fire({
                        icon: 'error',
                        title: this.error
                    })
                }
                if(this.error == "the cvv does not match that of the requested card"){
                    Toast.fire({
                        icon: 'error',
                        title: this.error
                    })
                }
                if(this.error == "the account does not have the requested amount"){
                    Toast.fire({
                        icon: 'error',
                        title: this.error
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
        getAccounts(){
            axios.get('/api/clients/current/accounts')
            .then(response =>{
                console.log(response)
                this.accounts = response.data
                console.log(this.accounts)
            })
        },
        amountFixed(number){
            return new Intl.NumberFormat('en-US', {style: 'currency',currency: 'USD', minimumFractionDigits: 2}).format(number);
        },
        logOut(){
            axios.post('/api/logout')
            .then((response) => {
                window.location.href = "/index.html"
            })
        },
    }
        
    }
).mount('#app')