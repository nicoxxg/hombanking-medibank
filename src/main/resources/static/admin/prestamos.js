const { createApp } = Vue
createApp({
    data() {
    return {
        loans:[],
        accounts: [],
        loan:[],
        loanPayments: [],
        datos: [],
        list: [],
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
                console.log(this.list);
                console.log(this.loans)
                this.loan = response.data.filter(loan => loan.id === id);
                this.loan.forEach(element => {
                    this.loanPayments = element.payments
                });
                console.log(this.loan);
                
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
             console.log(this.list)
             console.log(this.loanName)
             console.log(this.maxAmount)
             console.log(this.percentage)
             console.log(this.percentageInt)
             
            axios.post(`/api/loans/create` , {"name":this.loanName,maxAmount:this.maxAmount,listPayments:this.list,percentage:this.percentage,percentageInt:this.percentageInt})
            .then(response => {
                console.log("creado")
                window.location.href = "/web/loan-application.html"
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