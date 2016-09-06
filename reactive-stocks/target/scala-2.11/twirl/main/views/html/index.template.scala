
package views.html

import play.twirl.api._
import play.twirl.api.TemplateMagic._


     object index_Scope0 {
import models._
import controllers._
import play.api.i18n._
import views.html._
import play.api.templates.PlayMagic._
import play.api.mvc._
import play.api.data._

class index extends BaseScalaTemplate[play.twirl.api.HtmlFormat.Appendable,Format[play.twirl.api.HtmlFormat.Appendable]](play.twirl.api.HtmlFormat) with play.twirl.api.Template0[play.twirl.api.HtmlFormat.Appendable] {

  /**/
  def apply():play.twirl.api.HtmlFormat.Appendable = {
    _display_ {
      {
import play.mvc.Http.Context.Implicit._

Seq[Any](format.raw/*1.1*/("""<!DOCTYPE html>

"""),format.raw/*4.1*/("""
"""),format.raw/*5.1*/("""<html>
<head>
    <title>Reactive Stock News Dashboard</title>
    <link rel='stylesheet' href='"""),_display_(/*8.35*/routes/*8.41*/.Assets.at("lib/bootstrap/css/bootstrap.min.css")),format.raw/*8.90*/("""'>
    <link rel="stylesheet" media="screen" href=""""),_display_(/*9.50*/routes/*9.56*/.Assets.at("stylesheets/main.css")),format.raw/*9.90*/("""">
    <link rel="shortcut icon" type="image/png" href=""""),_display_(/*10.55*/routes/*10.61*/.Assets.at("images/favicon.png")),format.raw/*10.93*/("""">
    <script type='text/javascript' src='"""),_display_(/*11.42*/routes/*11.48*/.Assets.at("lib/jquery/jquery.min.js")),format.raw/*11.86*/("""'></script>
    <script type='text/javascript' src='"""),_display_(/*12.42*/routes/*12.48*/.Assets.at("lib/flot/jquery.flot.js")),format.raw/*12.85*/("""'></script>
    <script type='text/javascript' src='"""),_display_(/*13.42*/routes/*13.48*/.Assets.at("javascripts/index.js")),format.raw/*13.82*/("""'></script>
</head>
<body data-ws-url=""""),_display_(/*15.21*/routes/*15.27*/.Application.ws.webSocketURL(request)),format.raw/*15.64*/("""">
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container">
                <a class="brand" href="#">Reactive Stocks</a>
                <form id="addsymbolform" class="navbar-form pull-right">
                    <input id="addsymboltext" type="text" class="span2" placeholder="SYMBOL">
                    <button type="submit" class="btn">Add Stock</button>
                </form>
            </div>
        </div>
    </div>

    <div id="stocks" class="container">

    </div>
</body>
</html>
"""))
      }
    }
  }

  def render(): play.twirl.api.HtmlFormat.Appendable = apply()

  def f:(() => play.twirl.api.HtmlFormat.Appendable) = () => apply()

  def ref: this.type = this

}


}

/**/
object index extends index_Scope0.index
              /*
                  -- GENERATED --
                  DATE: Tue Sep 06 17:47:14 EDT 2016
                  SOURCE: /Users/brycerich/Desktop/Fall2016/ECE4012/SeniorDesign/RamblinTek/reactive-stocks/app/views/index.scala.html
                  HASH: aba29b959f1190c1bd19743cc3f7e78730452abc
                  MATRIX: 648->0|691->58|718->59|841->156|855->162|924->211|1002->263|1016->269|1070->303|1154->360|1169->366|1222->398|1293->442|1308->448|1367->486|1447->539|1462->545|1520->582|1600->635|1615->641|1670->675|1737->715|1752->721|1810->758
                  LINES: 25->1|27->4|28->5|31->8|31->8|31->8|32->9|32->9|32->9|33->10|33->10|33->10|34->11|34->11|34->11|35->12|35->12|35->12|36->13|36->13|36->13|38->15|38->15|38->15
                  -- GENERATED --
              */
          