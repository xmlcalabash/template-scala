package com.xmlcalabash.ext.templatescala

import com.xmlcalabash.model.util.{SaxonTreeBuilder, XProcConstants}
import com.xmlcalabash.runtime.{BinaryNode, XProcMetadata, XmlPortSpecification}
import com.xmlcalabash.steps.DefaultXmlStep
import com.xmlcalabash.util.{MediaType, MinimalStaticContext, TypeUtils}
import net.sf.saxon.om.{AttributeMap, EmptyAttributeMap}
import net.sf.saxon.s9api._

class Template extends DefaultXmlStep {
  override def inputSpec: XmlPortSpecification = XmlPortSpecification.ANYSOURCESEQ
  override def outputSpec: XmlPortSpecification = XmlPortSpecification.XMLRESULT

  private var binary = 0
  private var byteCount = 0
  private var markup = 0
  private var json = 0
  private var text = 0
  private var lineCount = 0

  override def receive(port: String, item: Any, metadata: XProcMetadata): Unit = {
    item match {
      case bin: BinaryNode =>
        binary += 1
        byteCount += bin.bytes.length
      case node: XdmNode =>
        if (metadata.contentType.textContentType) {
          text += 1
          lineCount += node.getStringValue.split("\\n").length
        } else {
          markup += 1
        }
      case _: XdmMap =>
        json += 1
      case _: XdmArray =>
        json += 1
      case _: XdmAtomicValue =>
        json += 1
      case _ => ()
    }
  }

  override def run(context: MinimalStaticContext): Unit = {
    super.run(context)

    val builder = new SaxonTreeBuilder(config)
    builder.startDocument(context.baseURI)
    builder.addStartElement(XProcConstants.c_result)

    var amap: AttributeMap = EmptyAttributeMap.getInstance()
    if (byteCount > 0) {
      amap = amap.put(TypeUtils.attributeInfo(new QName("", "bytes"), s"${byteCount}"))
    }
    builder.addStartElement(new QName("", "binary"), amap)
    builder.addText(s"${binary}")
    builder.addEndElement()

    builder.addStartElement(new QName("", "markup"))
    builder.addText(s"${markup}")
    builder.addEndElement()

    amap = EmptyAttributeMap.getInstance()
    if (lineCount > 0) {
      amap = amap.put(TypeUtils.attributeInfo(new QName("", "lines"), s"${lineCount}"))
    }
    builder.addStartElement(new QName("", "text"), amap)
    builder.addText(s"${text}")
    builder.addEndElement()

    builder.addStartElement(new QName("", "json"))
    builder.addText(s"${json}")
    builder.addEndElement()

    builder.addEndElement()
    builder.endDocument()
    consumer.receive("result", builder.result, new XProcMetadata(MediaType.XML))
  }

  override def reset(): Unit = {
    binary = 0
    byteCount = 0
    markup = 0
    json = 0
    text = 0
    lineCount = 0
  }
}
