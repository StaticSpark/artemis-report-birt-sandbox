define("new_base/js/main",[ "../lib/mustache"], function(require) {
    console.log("dating.....");
    // 通过 require 引入依赖
    var Mustache = require('../lib/mustache');

    var view = {
        title: "Joe",
        calc: function () {
            return 2 + 4;
        }
    };
    Mustache.render("{{title}} spends {{calc}}", view);

});
