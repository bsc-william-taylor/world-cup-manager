/**
 * Copyright (c) 2014 - William Taylor <wi11berto@yahoo.co.uk>
 *
 *	This software is provided 'as-is', without any express or implied warranty.
 *  In no event will the authors be held liable for any damages arising from
 *  the use of this software. Permission is granted to anyone to use this
 *  software for any purpose, including commercial applications, and to
 *  alter it and redistribute it freely, subject to the following
 *  restrictions:
 *
 *	1. The origin of this software must not be misrepresented;
 *     you must not claim that you wrote the original software.
 *	   If you use this software in a product, an acknowledgment
 *     in the product documentation would be appreciated
 *     but is not required.
 *
 *  2. Altered source versions must be plainly marked as such,
 *     and must not be misrepresented as being the original
 *     software.
 *
 *  3. This notice may not be removed or altered
 *     from any source distribution.
 *
 */
package framework.core;

import java.io.InputStream;
import java.util.ArrayList;

import org.w3c.dom.Document;

public class Font {
    /** Reserved space for the array of fonts **/
    private static final int RESERVED_SPACE = 100;
    /** The array of fonts that all font objects have access to **/
	private static ArrayList<Font> fonts;
    /** Our static initializer which sets up the array **/
    static {
        fonts = new ArrayList<Font>(RESERVED_SPACE);
    }

    /** Our xml reader to read font information **/
	private XmlReader xmlParser;
    /** The stream to the xml file **/
	private InputStream stream;
    /** Our document which will contain all information in memory **/
	private Document doc;
    /** The text map which has all characters **/
	private OpenglImage sprite;
    /** The filename for the texture **/
	private String texture;
    /** The name for the font **/
	private String name;

    /**
     * Our constructor which sets up a new font providing
     * that the font doesnt already exist in memory.
     *
     * @param xmlFile The file containing all the text information
     * @param texture_filename The location of the text filename
     * @param font_name The name of the font in the array
     */
	public Font(String xmlFile, String texture_filename, String font_name) {
        boolean duplicateFound = false;
        for(Font e : fonts) {
            if(e.name.equalsIgnoreCase(name)) {
                duplicateFound = true;
                break;
            }
        }

        if(!duplicateFound) {
            stream = ResourceManager.Get().GetResource(xmlFile);

            xmlParser = new XmlReader();
            doc = xmlParser.getDocument(stream);

            sprite = new OpenglImage();
            sprite.load(texture_filename, texture_filename);
            texture = texture_filename;
            name = font_name;

            fonts.add(this);
        }
	}

    /**
     * This function returns a reference to the font
     * noted by name
     * @param name The ID for the font to return
     * @return The font called name
     */
	public static Font get(String name) {
		for(Font e : fonts) {
			if(e.name.equalsIgnoreCase(name)) {
				return e;
			}
		} return null;
	}

    /**
     * @return Returns the textures filename
     */
	public String getTextureFilename() {
		return texture;
	}

    /**
     * @return Returns the object that parses the xml file
     */
	public XmlReader getParser() {
		return xmlParser;
	}

    /**
     * Returns the document object which contains
     * all the xml data
     *
     * @return The document which contains all xml data
     */
	public Document getDocument() {
		return doc;
	}

    /**
     * This function returns a reference to the stream.
     *
     * @return a reference to the stream which points to the xml file
     */
	public InputStream getStream() {
		return stream;
	}

    /**
     * The following clears all the fonts in the application
     */
	public static void clear() {
		fonts.clear();
	}
}
