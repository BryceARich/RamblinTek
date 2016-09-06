
// @GENERATOR:play-routes-compiler
// @SOURCE:/Users/brycerich/Desktop/Fall2016/ECE4012/SeniorDesign/RamblinTek/reactive-stocks/conf/routes
// @DATE:Tue Sep 06 18:15:00 EDT 2016

import play.api.routing.JavaScriptReverseRoute
import play.api.mvc.{ QueryStringBindable, PathBindable, Call, JavascriptLiteral }
import play.core.routing.{ HandlerDef, ReverseRouteContext, queryString, dynamicString }


import _root_.controllers.Assets.Asset

// @LINE:5
package controllers.javascript {
  import ReverseRouteContext.empty

  // @LINE:10
  class ReverseAssets(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:10
    def at: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Assets.at",
      """
        function(file) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "assets/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("file", file)})
        }
      """
    )
  
  }

  // @LINE:7
  class ReverseStockSentiment(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:7
    def get: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.StockSentiment.get",
      """
        function(symbol) {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "sentiment/" + (""" + implicitly[PathBindable[String]].javascriptUnbind + """)("symbol", encodeURIComponent(symbol))})
        }
      """
    )
  
  }

  // @LINE:5
  class ReverseApplication(_prefix: => String) {

    def _defaultPrefix: String = {
      if (_prefix.endsWith("/")) "" else "/"
    }

  
    // @LINE:6
    def ws: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.ws",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + { _defaultPrefix } + """" + "ws"})
        }
      """
    )
  
    // @LINE:5
    def index: JavaScriptReverseRoute = JavaScriptReverseRoute(
      "controllers.Application.index",
      """
        function() {
          return _wA({method:"GET", url:"""" + _prefix + """"})
        }
      """
    )
  
  }


}