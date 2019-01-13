package functorfunctor

import java.time.LocalDate

import natural_transformation.NaturalTransf.NaturalTransf
import simple.Id

/**
  * Functor Functor
  *
  * @see https://www.benjamin.pizza/posts/2017-12-15-functor-functors.html
  *
  * Laws:
  * -- identity
  * ffmap id == id
  *
  * -- composition
  * ffmap (eta . phi) = ffmap eta . ffmap phi
  */
trait FFunctor[FF[_[_]]] {
  def ffmap[F[_],G[_]](nat: NaturalTransf[F,G]): FF[F] => FF[G]
}


/*
 * Example based on:
 * Functor Functors - benjamin.pizza: https://www.benjamin.pizza/posts/2017-12-15-functor-functors.html
 */
object FFunctorExample {

  sealed trait CardType
  case object Visa extends CardType
  case object AmEx extends CardType
  case object Mastercard extends CardType

  object V1 {
    case class Form(email: String, cardType: CardType, cardNumber: String, cardExpiry: LocalDate)
    case class DraftForm(email: Option[String], cardType: Option[CardType], cardNumber: Option[String], cardExpiry: Option[LocalDate])

    def toForm: DraftForm => Option[Form] = {
      case DraftForm(Some(email), Some(cardType), Some(cardNumber), Some(cardExpiry)) => Some(Form(email, cardType, cardNumber, cardExpiry))
      case _ => None
    }
  }

  object V2 {
    case class FormTemplate[F[_]](email: F[String], cardType: F[CardType], cardNumber: F[String], cardExpiry: F[LocalDate])
    type Form = FormTemplate[Id]
    type DraftForm = FormTemplate[Option]

    def toForm: FormTemplate[Option] => Option[Form] = ???
  }
}