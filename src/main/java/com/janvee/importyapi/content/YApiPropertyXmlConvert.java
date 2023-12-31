package com.janvee.importyapi.content;

import org.jdom.Element;
import org.jetbrains.annotations.NonNls;

public interface YApiPropertyXmlConvert<T> {

    Element serialize(T property);

    T deserialize(@NonNls Element element);
}
