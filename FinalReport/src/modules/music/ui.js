function setSlider() {
    jQuery( function()  {
        jQuery('#slider').slider( {
            orientation: 'vertical', 
            animate: 'slow',
            range: 'min',
            value: 0.2, 
            min: 0,
            max: 1,
            step: 0.01,
            slide: function(event, ui) {
                audio.volume = ui.value
            },
            change: function(event, ui) {
                audio.volume = ui.value
            }
        });
    });
}

// gesture.js からのコール用
function getSliderValue() {
    var value = $('#slider').slider('value');
    return value
}

function setSliderValue(value) {
    $('#slider').slider('value', value);
}

setSlider();
