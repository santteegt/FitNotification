var ip;

$('#login').live('pagebeforeshow',function(e,data){    
    initHandlers();
    $('#loginf').submit(function(){        
        var usr = $('form :input[type=text]');
        var validU = validate(usr,'DEBE INGRESAR LAS CREDENCIALES','#login');
        var pwd = $('form :input[type=password]');
        var validP = validate(pwd,'DEBE INGRESAR LAS CREDENCIALES','#login');        
        if(!validU || !validP){
            return false;
        }
        $.mobile.showPageLoadingMsg();
        usr = usr.val();
        pwd = pwd.val();        
        $(":submit",this).attr("disabled", "disabled");                
        $(this).attr('method','POST').attr('action',ip+ '/proc/sign');
        return true;        
    });
});

$('#welcome').live('pagebeforeshow',function(e,data){    
    if(!navigator.cookieEnabled){
        modalMsg("Debe tener habilitada el uso de Cookies para poder utilizar la aplicación", "Error de Dispositivo")
    }else{
        $('.spinning').show();
    }
    /*e.preventDefault();
    if(data.toPage == null && data.prevPage.length != 0){
        var options = {dataUrl: 'index.html#login'};
        $.mobile.changePage($('#login'), options);
    }
    
    //window.history.forward();    
    //return false;
    //if (e.persisted) window.history.forward();*/
});

$('#welcome').live('pageinit',function(event){    
    var standalone = window.navigator.standalone,
    userAgent = window.navigator.userAgent,    
    safari = userAgent.indexOf( 'Safari' ) > -1,
    iOS = userAgent.indexOf( 'iPad' ) > -1
        || userAgent.indexOf( 'iPod' ) > -1
        || userAgent.indexOf( 'iPhone' ) > -1;    
    if( iOS ) {        
        if ( !standalone && safari ) {
            //browser
            $('#popup-qtip span')     
            .qtip({                
               content: {
                   title: {                       
                       text: 'Añadelo a tu Pantalla de Inicio',                       
                       button: false
                    },
                   text: 'Presiona +'             
                },
                position: {
                        my: 'bottom center', // Use the corner...
                        at: 'top center' // ...and opposite corner
                },
                show: {
                        event: false, // Don't specify a show event...
                        ready: true // ... but show the tooltip when ready
                },
                hide: false, // Don't specify a hide event either!
                style: {
                    title:{                            
                        'font-size': '30px'
                    },
                    classes: 'ui-tooltip-rounded ui-tooltip-undefined',
                    tip: {
                        corner:true,
                        height:10
                    }
                }
            }).show();
        }
    }
});

function blockMove() {
   event.preventDefault() ;
}

$(document).ready(function(){    
    $.get("prop.cf", function(data) {
        ip = data;        
    });
});

$('#welcome').live('pageshow',function(event, data) {
    $('.spinning').fadeOut(3000);
});