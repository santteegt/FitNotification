var ip, lin=true, first=true;

$( '#entorno' ).live( 'pagebeforeshow',function(event){    
    $.get("prop.cf", function(data) {        
        ip = data;
        $.getJSON(ip + '/proc/aedev', function(data) {            
            if(data.devices){
                event.preventDefault();
                var $page = $('#opciones');
                $.mobile.changePage($page, {transition: "fade"});
            }else{
                document.getElementById('view-p').setAttribute('content','user-scalable=0, width=device-width, minimum-scale = 1;');        
                getUserInfo();        
                if($('#devices').children().length == 0){            
                    getUserDevices(false);
                    getAuthorizationList(true);            
                }else{
                    getAuthorizationList(true);
                }
                $('#notifcollap').find('ul:jqmData(role=listview)').listview({refresh:true});
                $('#notifcollap').find('div:jqmData(role=collapsible)').collapsible({refresh:true});
                $('#notifcollap').trigger('updatelayout');        
                
            }

        });
    });        
});

$( '#opciones' ).live( 'pagebeforeshow',function(object){    
    document.getElementById('view-p').setAttribute('content','user-scalable=yes, width=device-width, minimum-scale = 1;');
    var navbar = $(this).find('div:jqmData(role=navbar)');
    $.get("prop.cf", function(data) {
        resetNavHeaders( navbar , 'opciones' );
        ip = data;        
        if($('#devices').children().length == 0){
            getUserDevices(true);        
        }
        $('#devices').listview('refresh');
    });
    
});

$( '#contrasena' ).live( 'pagebeforeshow',function(object){
    resetNavHeaders( $(this).find('div:jqmData(role=navbar)') , $(this).attr('id') );
    getUserInfo('#contrasena');
});

$( '#entorno, #autorizacion' ).live( 'pagebeforeshow',function(event){
    $.get("prop.cf", function(data) {
        ip=data;
    });    
});

$(document).ready(function(){    
    initHandlers();
    changePassHandler('#contrasena', true);    
    
    
    $('div:jqmData(role=page) #logout').on('click', function(event){
        logout();
        lin = false;
    });
       
    $('#authf').submit(function(e){        
        var dsc = $('form textarea');            
        var validD = dsc.val() == '' ? 
            validate(dsc,"CAMPO REQUERIDO", '#autorizacion'):changeClass(dsc,'widget-error','widget-ok');
        var chs = $('fieldset:jqmData(role=controlgroup) :checked');
        var validC = true
        if(chs.length == 0){
            $('fieldset:jqmData(role=controlgroup) :radio').each(function(index){
                var name = $(this).attr('id');                
                $('label[for=' + name + ']').addClass('widget-error');
            });
            validC = false;
        }else{
            $('fieldset:jqmData(role=controlgroup) :radio').each(function(index){
                var name = $(this).attr('id');                
                $('label[for=' + name + ']').removeClass('widget-error');
            });            
        }
        if(!validC || !validD){
            return false;
        }
        $.mobile.showPageLoadingMsg();        
        $(":submit",this).attr("disabled", "disabled");        
        
        $(this).attr('method','POST').attr('action',ip + '/proc/auth');
        /*$.ajax({      
          url: ip + "/proc/auth",
          type: "POST",
          data: {dvs: JSON.stringify(devices)},
          dataType: "json",
          success: function(data){
              $.mobile.hidePageLoadingMsg();
              clearstatusBar();
              parent.remove();
              $('#devices').listview('refresh');                      
          }
      });*/
        return true;        
    });
    
    
    $('#refresh-btn').on('click', function(event){
        getAuthorizationList(true);
    });
    
    $('#dev-to-ent').on('click', function(event){
        $.ajax( {
            url:ip + '/proc/gsysp',
            type:'POST',
            data:{parameter:'maxdev'},       
            dataType:'json',
            success:function(data){
                if($('#devices').children().length > data.value){
                    errorMessage("DEBE ELIMINAR UN DISPOSITIVO",'#opciones');            
                }else{
                    clearstatusBar('#opciones');
                    var $page = $( '#entorno' );                
                    $.mobile.changePage($page, {transition: "fade"});
                    //$.mobile.changePage(ip + "/entorno.html#entorno");
                }
            }
        });         
    });  
    
    $('#back-auth').on('click', function(event){
        var $page = $( '#entorno' );                
        $.mobile.changePage($page, {transition: "fade"});
        //$.mobile.changePage(ip + "/entorno.html#entorno");
        event.preventDefault();
    });  
    
    if(first){
        $('body').append("<div class='spinning'/>")
        $('.spinning').show();
        $('.spinning').fadeOut(3000);
        first = false;
    }
});

/**
 *Obtencion de los dispositivos de usuario
 */
function getUserDevices(ownPage){
    $.getJSON(ip + '/proc/ldev', function(data) {
                
        $.each(data, function(device, value) {
          setDevice(device, value);         
        });
        if(ownPage){
          $('#devices').listview('refresh');
        }            
        /**
        *Funcion para el borrado de un dispositivo
        **/
        $('#devices .delete').on('click', function(event){
          clearstatusBar('#opciones');
          $.mobile.showPageLoadingMsg();
          var parent = $(this).parent();
          var current = parent.attr('id');        
          var devices = {};
          var count = 0;
          var numDevice = 0;
          $('#devices li').each(function(index){
              count++;
              var name = $(this).attr('id').substring($(this).attr('id').indexOf('-')+1,$(this).attr('id').length);
              if(devices[name] != undefined) return;
              devices[name] = {};
              devices[name]['Modelo'] = $(this).jqmData('model');
              if(current == $(this).attr('id')){                  
                  devices[name]['Estado'] = '0';
                  numDevice = index;
              }else{                  
                  devices[name]['Estado'] = '1';                  
              }              
          });          
          if(count > 1 && numDevice < $('#devices li').length -1){
              $.ajax({      
                  url: ip + "/proc/upd",
                  type: "POST",
                  data: {dvs: JSON.stringify(devices)},
                  dataType: "json",
                  success: function(data){
                      $.mobile.hidePageLoadingMsg();
                      clearstatusBar('#opciones');
                      parent.remove();
                      $('#devices').listview('refresh');                      
                  },
                  error:function(data){
                      getError();                                            
                    }
              });

          }else{               
              $.mobile.hidePageLoadingMsg();
              errorMessage("NO PUEDE ELIMINAR EL DISPOSITIVO CONECTADO",'#opciones');
          }          
        });      
    });
}

/**
* Obtiene la lista de autorizaciones del servidor
*/
function getAuthorizationList(ownPage){    
    $.mobile.showPageLoadingMsg();
    $.ajax({      
          url: ip + '/proc/notif',
          type: "POST",
          dataType:'json',
          success: function(data){              
                var exist = false;
                $('#notifcollap').children().remove();                
                $.each(data, function(type, notiflist) {
                    fillNotifCollapsible(type, notiflist);
                    exist = true;
                });
                if(!exist){
                    $('#none-n').show();
                }else{
                    $('#none-n').hide();
                }
                if(ownPage){                    
                    $('#notifcollap').find('ul:jqmData(role=listview)').listview({refresh:true});
                    $('#notifcollap').find('div:jqmData(role=collapsible)').collapsible({refresh:true});
                    $('#notifcollap').trigger('updatelayout');
                }
                $.mobile.hidePageLoadingMsg();
                /**
                 * Funcion para la apertura/renovacion de una autorizacion
                 */
                $('#notifcollap div[class=ui-btn-text]').on('click', function(event){
                    $('#e-general').remove();
                    var notif = $(this).parent().parent();
                    //$('#consolidado').jqmData('url','#consolidado?status='+notif.jqmData('status')+'&id='+notif.jqmData('token'));
                    var request = {};
                    request.status = notif.jqmData('status');
                    request.id = notif.jqmData('token');
                    if(request.status == '0'){                
                        var message = $('<p />', {text: "Ingrese La Palabra Clave"}),
                        input = $('<input />', {val: ''}),
                        ok = $('<button />', { 
                                text: 'Renovar',
                                click: function() {
                                    $.mobile.loadingMessage = 'Peticion nuevo token...';
                                    $.mobile.showPageLoadingMsg();
                                    request.keyword = input.val();
                                    authorizationRequest(request, ownPage);                            
                                }
                        }),
                        cancel = $('<button />', {
                                text: 'Cancelar',
                                click: function() {  }
                        });

                        dialogue( message.add(input).add(ok).add(cancel), 'Renovación de Token' );

                    }else{
                        request.keyword = "";
                        authorizationRequest(request, ownPage);
                    }

                });              
          },
          error:function(data){
              getError();                                   
        }
    });     
}

/**
 * Funcion que pide la renovacion de autorizacion o el detalle de la misma
 **/
function authorizationRequest(request, ownPage){       
    $.ajax( {
        url:ip + '/proc/authr',
        type:'POST',
        data:request,       
        dataType:'json',
       
        success:function(data){
            if(data.status == '0'){                
                //$.mobile.showPageLoadingMsg();
                getAuthorizationList(ownPage);
                $.mobile.loadingMessage = 'Cargando...';
            }else{
                $.mobile.changePage(ip + "/entorno.html#e-general");
            }
           
        },
        error:function(data){
            getError();           
        }
    });
    
}

/**
 *Obtencion de informacion de usuario para el entorno
 */
function getUserInfo(container){
    $.mobile.showPageLoadingMsg();
    $.get("prop.cf", function(data) {
        ip = data;
        $.getJSON(ip + '/proc/usinf', function(data) {
            if(container){            
                $(container + " #textpn").val(data.name);
                $(container + " #textpc1").val(data.email);
            }else{
                $('#titulo span').html('').html('HOLA '+ data.name);
            }
            $.mobile.hidePageLoadingMsg();
        });
    });
}

/**
 * Manejo de pagins dinamicas de autorizacion
 */
$(document).bind( "pagebeforechange", function( e, data ) {

    /*if(data.options.fromPage != undefined && data.options.toPage == undefined ){
        e.preventDefault();
    }*/
    
    if ( typeof data.toPage === "string" ) {            
        var u = $.mobile.path.parseUrl( data.toPage ),
                re = /^#e-general/;
        if ( u.hash.search(re) !== -1 && $('#e-general').length == 0) {
            $.mobile.showPageLoadingMsg();
            e.preventDefault();
            $.getJSON(ip + '/proc/authd', function(datajSON) {
                document.getElementById('view-p').setAttribute('content','user-scalable=yes, width=device-width, minimum-scale = 1;');
                authorizationPages( u, data.options, datajSON );                
            });
            
        }        
    }
});
