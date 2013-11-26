/**
 *Enhance authorization pages sent by server
 **/
function authorizationPages(urlObj, options, datajSON){        
    var pageSelector = urlObj.hash,    
    $page,
    markup = "";
    
    for(var i=datajSON.pages.length-1;i>=0;i--){
        markup += datajSON.pages[i];
    }
    $('#authcontentpages').html(markup);        
    addHeader(datajSON.ids);
    addFooter();    
    for(i=datajSON.ids.length-1;i>=0;i--){
        var idPage = datajSON.ids[i];
        $page = $( idPage );
        $page.page();
        /** Evento para el funcionamiento correcto de botones navbar */
        $('#' + idPage).live('pagebeforeshow',function(e,data){
            resetNavHeaders( $(this).find('div:jqmData(role=navbar)') , $(this).attr('id') );
        });        
    }    
    $page = $( pageSelector );
    options.dataUrl = urlObj.href;
    $.mobile.changePage( $page, options );
}

/**
 * Agrega el markup comun de pie de pagina para la paginas de la autorizacion
 */
function addFooter(){    
    $('#authcontentpages div:jqmData(role=footer)').each(function(index){
        $(this).html("<div data-role='navbar'>\n<ul>\n"+
                    "<li><a href='#autorizacion' id='autorizacion-btn' data-icon='check' data-iconpos='top' data-transition='fade'>Autorizacion</a></li>\n"+
                    "<li><a href='entorno.html#opciones' id='opciones-btn' data-icon='gear' data-iconpos='top' data-transition='fade'>Opciones</a></li>\n"+
                    "</ul>\n</div>\n");
    });
}

/**
 * Agrega encabezado de paginas que posee la autorizacion
 */
function addHeader(ids){ 
    var endmarkup = "</ul></div></div>";
    $('#authcontentpages div:jqmData(role=header)').each(function(index){
        var markup = "<div data-role='header class='barra'>"+                
            "<a href='entorno.html#entorno' data-icon='home' data-iconpos='notext' data-role='button' class='ui-btn-left'>Principal</a>"+
            "<img src='css/images/logo.png'/>"+
            "<span >FitNotification</span>"+			
            /*"<a id='logout' href='' data-icon='grid' data-role=button class='ui-btn-right'>Salir</a>"+*/
            "<div data-role='navbar'>"+
                "<ul>";                            
        for(var i=ids.length-1;i>=0;i--){
            markup += "<li><a href='#" + ids[i] + "' id='" + ids[i] + "-btn' data-paget='#"+ids[i]+ "'>" + ids[i] + "</a></li>";            
        }
        markup += endmarkup;        
        $(this).html(markup);
        /** Evento click para cada pagina **/
        $(this).find('ul a').each(function(idx){
            $(this).on('click', function(event){                
                var $page = $( $(this).attr('data-paget') );                
                $.mobile.changePage($page, {transition: "fade"});                
                event.preventDefault();
            });
        });
    });    
    /*$('#authcontentpages div:jqmData(role=page) #logout').on('click', function(event){
        logout();        
    });*/
}