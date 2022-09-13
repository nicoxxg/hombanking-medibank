const { createApp } = Vue
createApp({
    data() {
    return {
        loans:[],
        accounts: [],
        loan:[],
        loanPayments: [],
        datos: [],
        amount: 0,
        payments: 0,
        number: "",
        loanId: 0,
        loanName: "",
        maxAmount: 0,
        error: "",
        percentageInt: 0,
        percentage: 0,
    }
    },
    created(){
        this.getLoans()
        this.getAccounts()
        this.getClient()
    },
    methods:{
        getClient(){
            axios.get("/api/clients/current")
            .then(response => {
                this.datos = response.data;
                console.log(this.datos);
            })
        },
        getLoans(id){
            axios.get(`/api/loans`)
            .then(response =>{
                this.loans = response.data
                console.log(this.loans)
                this.loan = response.data.filter(loan => loan.id === id);
                this.loan.forEach(element => {
                    this.loanPayments = element.payments
                });
                console.log(this.loan);
                this.loan.map(element => this.loanId = element.id);
                this.loan.map(element => this.loanName = element.name);
                this.loan.map(element => this.maxAmount = element.maxAmount);
                this.loan.map(element => this.percentageInt = element.percentageInt);
                this.loan.map(element => this.percentage = element.percentage);

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
        addLoans(){
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
            }).then((result) =>{
                if(result.isConfirmed){
                axios.post(`/api/loans`, {"loanId":this.loanId,"amount":this.amount,"payments":this.payments,"number":this.number})
                .then((response) =>{swal.fire({
                    icon: 'success',
                    title: 'your loan was succesfull',
                    showConfirmButton: false,
                    timer: 1500,
                    timerProgressBar: true,
                }).then((result) => {
                    window.location.href = '/web/accounts.html'
                })
                }).catch((error) =>{
                    this.error = error.response.data;
                    console.log(this.error);
                    if(this.error == "please complete the data") {
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "please enter an amount other than 0") {
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "the account you selected does not exist") {
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "the requested amount exceeds the requested loan") {
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "the amount of requested payments exceeds the amount of loan payments") {
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "the account does not exist") {
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                    if(this.error == "the account does not belong to the verified customer") {
                        Toast.fire({
                            icon: 'error',
                            title: this.error
                        })
                    }
                })
                
                }
            })
            
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