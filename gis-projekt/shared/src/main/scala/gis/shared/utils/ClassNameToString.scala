package gis.shared.utils

trait ClassNameToString {
  override def toString: String = {
    getClass.getSimpleName
  }
}
