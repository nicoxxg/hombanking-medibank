


const { createApp } = Vue

createApp({
    data() {
    return {
        accounts: [],
        datos:[],
        usuario:{
            balance: "",
            creationDate: "" ,
            Number: "",
        },
        totalBalance: 0,
        lastTransaction: [],
        transactions: [],
        loans:[],
        idCliente: 0,
        color:[
            "NORMAL",
            "SILVER",
            "GOLD",
            "TITANIUM"
        ],
        accountType: "",


        
    }
    },
    created(){
        this.crearCuentas()
        this.seeAccount()
    },
    methods:{
        deleteAccount(id){
            swal.fire({
                icon: 'warning',
                title: 'Are you sure to delete this account?',
                showDenyButton: true,
                confirmButtonText: 'yes',
                denyButtonText: `no`,
            }).then((response) => {{
                if (response.isConfirmed){
                    axios.patch(`/api/clients/current/accounts/${id}`)
                .then(response => {
                    console.log("deleted");
                    window.location.reload()
                })
                }
            }})
            
        },
        seeAccount(){
            axios.get('/api/clients/current/accounts')
            .then(response => {
                this.accounts = response.data.sort((a,b) =>  b.id - a.id)
                console.log(this.accounts)
                console.log(response)
                this.accounts.forEach(element => {
                    let balance = element.balance
                    return this.totalBalance += balance
                });
                this.accounts.forEach(element => {
                    let transaction = element.transactions
                    transaction.forEach(element => {
                        this.transactions.push(element)
                    })
                });
                this.lastTransaction = this.transactions.sort((a,b) => b.id - a.id)
            })
        },
        crearCuentas(){
            
            axios.get(`/api/clients/current`)
            .then((response) => {
                
                console.log(response)
                this.loans = response.data.loans
                this.datos = response.data
                this.idCliente = response.data.id
                
                //
                
                //
                
                
            })
            .catch(function (error) {
                this.mensajeformularioLogIng= error.response.data
                if (error.response) {
                  // The request was made and the server responded with a status code
                  // that falls out of the range of 2xx
                    console.log(error.response.data);
                    console.log(error.response.status);
                    console.log(error.response.headers);
                } else if (error.request) {
                  // The request was made but no response was received
                  // error.request is an instance of XMLHttpRequest in the browser and an instance of
                  // http.ClientRequest in node.js
                    console.log(error.request);
                } else {
                  // Something happened in setting up the request that triggered an Error
                    console.log('Error', error.message);
                }
                console.log(error.config);
            })
        
        },
        logOut(){
            axios.post('/api/logout')
            .then((response) => {
                window.location.href = "/index.html"
            })
        },
        createAccount(){
            Swal.fire({
                icon: 'warning',
                title: 'Select field validation',
                input: 'select',
                inputOptions: {
                    'corriente': 'common',
                    'ahorro': 'saving',
                },
                inputPlaceholder: 'Select a account',
                showCancelButton: true,
                
                }).then(response =>{
                    console.log();
                    if(response.value){
                        axios.post("/api/clients/current/accounts",`accountType=${response.value}`)
                        .then(response => {
                            console.log(response)
                            window.location.reload()
                        })
                    }
                
            })
              
              
            /*
            Swal.fire({
                title: 'please select a type of your account',
                icon: 'info',
                content: "select",
                inputOptions: {
                    'type of account':{
                        corriente: 'corriente',
                        'ahorro': 'ahorro',
                    }
                },
                inputPlaceholder: 'Select a fruit',
            })*/
            
        }
        },
        
    }
).mount('#app')