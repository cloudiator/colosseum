package views.helper.bootstrap

import views.html.helper.bootstrap._
import views.html.helper.FieldConstructor

object BasicField {
  implicit val bootstrapConstructor = FieldConstructor(basicField.f)
}