var ip, first=true;

/**
 *Manejadores antes de iniciar el entorno
 **/
$(document).bind('pageinit', function(){
    initHandlers();
    $('div:jqmData(role=page) #logout').on('click', function(event){
        logout();        
    });
    $.mobile.listview.prototype.options.filterPlaceholder = 'Buscar...';
    
    $('#configuracion input').each(function(i,input){
        $(input).on('change',function(){            
            $(this).attr('data-change','1');
        })
    });
});

$(document).ready(function(){    
    $('#refresh-btn').on('click', function(event){
        getInfo('#configuracion');
    });
    
    /**
     * Actualizacion de parametros del Servidor
     **/
    $('#submitConf').on('click', function(event){
        clearstatusBar('#configuracion');
        $.mobile.showPageLoadingMsg();
        var json = {}, field, existChange=false;        
        $('#configuracion input').each(function(i,input){
            if($(this).jqmData('change') =='1'){
                existChange = true;
                field = $(this).attr('id');
                $(this).attr('data-change','0');
                json[field.substring(0,field.indexOf('-txt'))] = $(this).val();
            }
        });
        if(existChange){
            $.ajax({      
              url: ip + "/proc/upsrd",
              type: "POST",
              data: {flds: JSON.stringify(json)},
              dataType:'json',
              success: function(data){                  
                  OK();
                  getInfo('#configuracion');                  
              },
              error:function(data){
                  getErrorFooter('#configuracion');
                }
            });
        }else{
            $.mobile.hidePageLoadingMsg();
            errorMessage('NO HAY CAMBIOS PARA MANTENER', '#configuracion');
            //modalMsg("No Hay Cambios Para Mantener", "Información");
        }
        
    });
    
    /**
     * Reinicio de contrasena de usuario
     **/
    $('#contrasena').find(':jqmData(role=button)').on('click', function(event){
        var user = $('#contrasena input[name=user]');
        var validP = validate(user,'DEBE INGRESAR UN USUARIO','#contrasena');
        if(!validP) return;
        clearstatusBar('#contrasena');
        $.mobile.showPageLoadingMsg();
        var json = { user: user.val() };
        $.ajax({      
              url: ip + "/proc/rupas",
              type: "POST",
              data: json,
              dataType:'json',
              success: function(data){
                  $.mobile.hidePageLoadingMsg();
                  OK();
              },
              error:function(data){
                  $.mobile.hidePageLoadingMsg();
                  getErrorFooter('#contrasena');                     
              }
        });
    });
    
    if(first){
        $('body').append("<div class='spinning'/>")
        $('.spinning').show();
        $('.spinning').fadeOut(3000);
        first = false;
    }
    
});


$( '#configuracion' ).live( 'pagebeforeshow',function(event){
    resetNavHeaders( $(this).find('div:jqmData(role=navbar)') , $(this).attr('id') );
    $.get("prop.cf", function(data) {
        ip = data;
        getInfo('#configuracion');
    });    
    
});

$('#sesiones, #contrasena').live('pagebeforeshow',function(e,data){
    resetNavHeaders( $(this).find('div:jqmData(role=navbar)') , $(this).attr('id') );
    $('#contrasena #user').bind('change',function(e, ui){
       cleanError($(this)); 
    });
});

$('#autorizaciones').live('pagebeforeshow',function(e,data){
    resetNavHeaders( $(this).find('div:jqmData(role=navbar)') , $(this).attr('id') );
    getErroAuthorizationList();
});

/**
 * Funcionalidad para recarga al cerrar sesion
 */
$(document).bind( "pagebeforechange", function( e, data ) {    
    if ( typeof data.toPage === "string" ) {            
        var u = $.mobile.path.parseUrl( data.toPage );
        
        if(u.filename ==='index.html'){
            $('#welcome').live('pagebeforeshow',function(event,data){                    
                  window.location.reload(true);
            });
        }
    }
});

/**
 * Obtiene los parametros del servidor
 */
function getInfo(container){
    clearstatusBar(container);
    $.mobile.showPageLoadingMsg();
    $.ajax({      
          url: ip + "/proc/servd",
          type: "POST",
          dataType:'json',
          success: function(data){              
              $.each(data, function(page, register) {
                  manageFields(register, page);
              }); 
              $.mobile.hidePageLoadingMsg();              
          },
          error:function(data){
              getErrorFooter(container);                                   
        }
    });
}

/**
 * Anade los valores de los campos traidos en la peticion
 */
function manageFields(register, page){
    
        
    if(page == 'sesiones'){
        $('#listsessions').children().remove();
        if(register.length == 0){            
            $('#none-s').show();
            return;
        }else{            
            var list='';
            if(register.length){
                $.each(register, function(k,value) {         
                    list += "<li data-user='"+ value.user + "'><a href=''>\n"            
                    + "<h3 class='ui-li-heading'>" + value.user + "</h3>\n"
                    + "<p class='ui-li-desc'>Fecha Inicio Sesión: <b>" + value.date +"</b></p>\n</a>\n\n"
                    + "<a class='delete' href=''></a>\n</li>\n";                        
                }); 
            }else{                
                list += "<li data-user='"+ register.user + "'><a href=''>\n"            
                + "<h3 class='ui-li-heading'>" + register.user + "</h3>\n"
                + "<p class='ui-li-desc'>Fecha Inicio Sesión: <b>" + register.date +"</b></p>\n</a>\n\n"
                + "<a class='delete' href=''></a>\n</li>\n";                    
            }                
            $('#none-s').hide();                
            $('#listsessions').append(list);
            /**
             * Funcion para el borrado de la sesion escogida de usuario
             */
            $('#listsessions .delete').on('click', function(event){
                $.mobile.showPageLoadingMsg();
                var parent = $(this).parent();
                var json = {user: parent.attr('data-user') };        

                $.ajax({      
                    url: ip + "/proc/fussn",
                    type: "POST",
                    data: json,
                    dataType: "json",
                        success: function(data){
                            $.mobile.hidePageLoadingMsg();                                
                            parent.remove();
                            $('#listsessions').listview('refresh');
                            if($('#listsessions').children().length == 0) $('#none-s').show();
                            OK();
                      }
                  });

            });            
            $('#listsessions').listview('refresh');                
        }

    }       
    else{
        $.each(register, function(k,value) {
            $('#' + page + ' #' + k + '-txt').val(value);
        });
    }       
}

/**
 *Obtiene la lista de autorizacion con errores del CORE al momento de su autorizacion
 **/
function getErroAuthorizationList(){    
    $.mobile.showPageLoadingMsg();
    $.getJSON(ip + '/proc/notif', function(data) {        
        var exist = false;
        $('#notifcollap').children().remove();        
        $.each(data, function(type, notiflist) {
            fillEAuthCollapsible(type, notiflist);
            exist = true;
        });
        if(!exist){
            $('#none-n').show();
        }else{
            $('#none-n').hide();
        }        
        $('#notifcollap').find('ul:jqmData(role=listview)').listview({refresh:true});
        $('#notifcollap').find('div:jqmData(role=collapsible)').collapsible({refresh:true});
        $('#notifcollap').trigger('updatelayout');        
        $.mobile.hidePageLoadingMsg();
        /**
         * Funcion para la eliminacion de una autorizacion
         */
        $('#notifcollap .delete').on('click', function(event){
            var notif = $(this).parent();            
            var request = {id:notif.jqmData('token')};            
            deleteAuthorization(request);                       
        });

    });
}

function fillEAuthCollapsible(type, notiflist){
    var max = 0;    
    var list = "<ul data-role='listview' data-inset='true' data-theme='c'  data-split-icon='delete' data-split-theme='c' >\n";
    $.each(notiflist, function(k,value) {         
        list += "<li " + (value.Estado == 0 ? "data-theme='e' data-status='0' ":"data-status='1' " ) + "data-token='" + value.Id + "'><a href=''>\n"
        + "<p class='ui-li-aside ui-li-desc'>Fecha:<strong>"+ value.Fecha + "</strong></p>\n"
        + "<h3 class='ui-li-heading'>" + value.Autorizacion + "</h3>\n"
        + "<p class='ui-li-desc'>Oficial: " + value.Oficial +"</p>\n"
        + "<p class='ui-li-desc'><strong>Error: " + value.Mensaje +"</strong></p>\n</a>\n"
        + "<a class='delete' href=''></a>\n</li>\n";                    
        max = k+1;        
    });    
    var head = "<div data-role='collapsible' data-theme='a' data-content-theme='a' id='" + type.substring(5,type.length) + "'>\n"
    +"<h3>\n<span class='auth-count'>"+type.substring(8,type.length)+"</span>\n"
    +"<span id='count' class='count-item ui-li-count ui-btn-up-c ui-btn-corner-all'>" + max + "</span>\n</h3>\n";
    
    $('#notifcollap').append(head + list + "</ul>\n</div>\n");    
}

/**
 * Funcion que elimina la autorizacion
 **/
function deleteAuthorization(request){
    clearstatusBar('#autorizaciones');
    $.mobile.showPageLoadingMsg();
    $.ajax( {
        url:ip + '/proc/dauth',
        type:'POST',
        data:request,       
        dataType:'json',
       
        success:function(data){            
                $.mobile.changePage(ip + "/admin.html");           
        },
        error:function(data){
            getErrorFooter('#autorizaciones');
        }
    });    
}