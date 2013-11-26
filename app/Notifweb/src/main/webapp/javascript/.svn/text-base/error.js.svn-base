 $('#error').live('pagebeforeshow',function(e,data){
     var ip;
     $.get("prop.cf", function(data) {
        ip = data;
        var sactive = 0;

        $('#close').on('click', function(event){
            if(sactive == 0){
                $.mobile.changePage(ip + "/index.html");
            }

        });

        $.getJSON(ip + '/proc/error', function(data) {      
          $('#message').append(data.message);              
          sactive = data.sactive;
          if(sactive == 1 ){
              $('#stack').append(data.stacktr); 
          }else{                  
              $('div[data-role=collapsible]').hide();
          }
        });
    });
     
 });
 
 function blockMove() {
   event.preventDefault() ;
}