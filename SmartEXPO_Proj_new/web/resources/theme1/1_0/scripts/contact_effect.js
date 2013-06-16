/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function() {

    $("#lyr_g").fadeIn('slow', function() {
        $("#lzh_g").fadeIn('slow', function() {
            $('#lyr_g').fadeOut('slow');
            $("#zyq_g").fadeIn('slow', function() {
                $('#lzh_g').fadeOut('slow');
                $("#lby_g").fadeIn('slow', function() {
                    $('#zyq_g').fadeOut('slow', function() {
                        $('#lyr_g').fadeIn('slow');
                        $('#lzh_g').fadeIn('slow');
                        $('#zyq_g').fadeIn('slow', function() {
                            $('#lyr_g').delay(1000).fadeOut('slow');
                            $('#lzh_g').delay(1000).fadeOut('slow');
                            $('#lby_g').delay(1000).fadeOut('slow');
                            $('#zyq_g').delay(1000).fadeOut('slow', function() {
                                $("#lyr").mouseenter(function() {
//        $("#lyr_g").clearQueue();
                                    $("#lyr_g").fadeIn('fast');
                                });

                                $("#lby").mouseenter(function() {
//        $("#lby_g").clearQueue();
                                    $("#lby_g").fadeIn('fast');
                                });


                                $("#lzh").mouseenter(function() {
//        $("#lzh_g").clearQueue();
                                    $("#lzh_g").fadeIn('fast');
                                });


                                $("#zyq").mouseenter(function() {
//        $("#zyq_g").clearQueue();
                                    $("#zyq_g").fadeIn('fast');
                                });

                                $("#lyr").mouseleave(function() {
//        $("#lyr_g").clearQueue();
                                    $("#lyr_g").fadeOut('fast');
                                });

                                $("#lzh").mouseleave(function() {
//        $("#lzh_g").clearQueue();
                                    $("#lzh_g").fadeOut('fast');
                                });

                                $("#lby").mouseleave(function() {
//        $("#lby_g").clearQueue();
                                    $("#lby_g").fadeOut('fast');
                                });

                                $("#zyq").mouseleave(function() {
//        $("#zyq_g").clearQueue();
                                    $("#zyq_g").fadeOut('fast');
                                });

                            });
                        });
                    });
                });
            });
        });
    });



});
