let vueApp = new Vue({
    el: "#app",
    data: {
        url: "",
        canSubmit: true,
        title: ""
    },
    methods:
    {
        copyUrl: function()
        {
            if(this.canSubmit) {
                return ;
            }
            navigator.clipboard.writeText(this.url) ;
            $('[data-toggle="tooltip"]').tooltip();

            this.reset() ;
        },
        submitForm: function()
        {
            if(!this.canSubmit) {
                return ;
            }

            var ajaxCall = $.ajax({
                url: "/api/shrink",
                method: "POST",
                dataType: "json",
                data: JSON.stringify({sourceUrl: this.url}),
                contentType: "application/json"
            }) ;

            ajaxCall.fail((xhr) =>
            {
                let jsonError = JSON.parse(xhr.responseText) ;
                swal("Attention", jsonError.message, "warning") ;
            }) ;

            ajaxCall.done((json) =>
            {
                console.log(json) ;
                this.url = json.shrinkedUrl ;
                this.canSubmit = false ;
                this.title = "Copié"
            }) ;
        },
        reset: function()
        {
            this.url = "" ;
            this.canSubmit = true ;
            this.title = "" ;
        }
    },
    computed:
    {
        copyLinkClass: function()
        {
            if(!this.canSubmit) {
                return "input-group-addon btn btn-link" ;
            }

            return "input-group-addon" ;
        }
    }
}) ;