const { createApp } = Vue

createApp({
    data() {
    return {
        accounts: [],
        datos:[],
        botonEditar: false,
        clientes: [],
        form: {
        "name": "",
        "lastName": "",
        "email": "",
        },
        id: "",
        cuentasMostradas: [],


    }
    },
    created(){
        this.axiosGet()
    },
    methods:{
        axiosGet(){
            axios.get('/rest/clients')
            .then((response) => {
                console.log(response)
                this.clientes = response.data
                this.form
                console.log(this.clientes)

            })
            
        },
        agregarYeditar(){
            //metodo 3
                if (this.botonEditar == true) {
                    axios.put(`/rest/clients/${this.id}`)
                    //axios.put(`/rest/clients/${this.id}`,this.form) es otra forma
                    .then(() => {
                    this.botonEditar = false;
                    this.form.name = ""
                    this.form.lastName = ""
                    this.form.email = ""
                })
                    .then(() => this.axiosGet())
                    .catch(() => console.log("no funciona"))
                }else{
                    axios.post('/rest/clients', this.form)
                    .then(() => {
                        console.log(this.form)
                    })
                    .then(() => this.axiosGet())
                }
                /*
            axios.post('/api/clients', this.form)
            .then(() => this.axiosGet())//es para retornar
            .then(() => this.axiosGet())*/
        },

        eliminar(id){
            axios.delete(`/rest/clients/${id}`)
            .then(() => this.axiosGet())
            .catch((error) => console.log("no funciona"+ " " + error))
        },
        editar(id){
            //metodo 3
            axios.get(`/rest/clients/${id}`)
            .then((response) => {
                
                this.id = id
                this.botonEditar = true
                this.form.name = response.data.name
                this.form.lastName = response.data.lastName
                this.form.email = response.data.email
            }
            )
            
            //metodo 2
            /*
            
            axios.get(`/clients/${id.split('clients/')[1]}`)
            .then((response) => {
                this.form.name = response.data.name
                this.form.lastName = response.data.lastName
                this.form.email = response.data.email
                this.form.age = response.data.age
            }
            )*/
            
            //metodo 1
/*
            axios.put(`/clients/${id.split('clients/')[1]}`,this.form)
            .then((response) => {
                console.log(response);
            })
            .then(() => this.axiosGet())
            .then(() => this.axiosGet())
            .catch(() => console.log("no funciona"))
*/
        },
        mostrarCuentas(id){
            axios.get(`/rest/clients/${id}`)
            .then((response) => this.cuentasMostradas = response.data.accounts)
        },
        
    }
}).mount('#app')