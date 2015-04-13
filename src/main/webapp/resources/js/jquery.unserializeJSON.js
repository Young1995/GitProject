;(function ($) {
  $.fn.unserializeJSON = function (json) {
    var that = this;
    $.each(json, function (key, val) {
      var el = that.find('[name=' + key + ']');
      if (el.length > 0) {
        if(el.is('select') && el.prop('multiple')) {
          //reset all options
          el.find('option').prop('selected', !1);

          $.each(val, function (i, v) {
            el.find('option[value=' + v + ']')
              .prop('selected', !0);
          });
        } else if (el.is(':radio')){
            el.filter('[value=' + val + ']')
              .prop('checked', !0);
        } else {
            el.val(val);
        }
      }
    });
  };
})(jQuery);