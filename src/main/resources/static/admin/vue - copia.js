const { createApp } = Vue

createApp({
    data() {
    return {

        clients: [],
        
    }
    },
    created(){
        this.axiosGet()
    },
    methods:{
        axiosGet(){
            axios.get('http://localhost:8080/clients')
            .then((response) => {
                this.clients = response.data._embedded.clients
                console.log(this.clients);
            })
            .catch(function () {
                console.log("no funciona");
        });
        }
    
    }
}).mount('#app')