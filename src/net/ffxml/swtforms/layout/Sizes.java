/*
 * Copyright (c) 2002-2006 JGoodies Karsten Lentzsch. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 * 
 *  o Redistributions of source code must retain the above copyright notice, 
 *    this list of conditions and the following disclaimer. 
 *     
 *  o Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution. 
 *     
 *  o Neither the name of JGoodies Karsten Lentzsch nor the names of 
 *    its contributors may be used to endorse or promote products derived 
 *    from this software without specific prior written permission. 
 *     
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR 
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, 
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE 
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. 
 */

package net.ffxml.swtforms.layout;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import net.ffxml.swtforms.util.DefaultUnitConverter;
import net.ffxml.swtforms.util.UnitConverter;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 * Consists only of static methods that create and convert sizes as required by
 * the FormLayout. The conversion of sizes that are not based on pixel is
 * delegated to an implementation of {@link UnitConverter}. The conversion
 * methods require the layout container as parameter to read its current font
 * and resolution.
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.2 $
 * @see Size
 * @see UnitConverter
 * @see DefaultUnitConverter
 */
public final class Sizes {
	private static final long serialVersionUID = 1L;

	// Common Constant Sizes ************************************************

	public static final ConstantSize ZERO = pixel(0);

	public static final ConstantSize DLUX1 = dluX(1);
	public static final ConstantSize DLUX2 = dluX(2);
	public static final ConstantSize DLUX3 = dluX(3);
	public static final ConstantSize DLUX4 = dluX(4);
	public static final ConstantSize DLUX5 = dluX(5);
	public static final ConstantSize DLUX6 = dluX(6);
	public static final ConstantSize DLUX7 = dluX(7);
	public static final ConstantSize DLUX8 = dluX(8);
	public static final ConstantSize DLUX9 = dluX(9);
	public static final ConstantSize DLUX11 = dluX(11);
	public static final ConstantSize DLUX14 = dluX(14);

	public static final ConstantSize DLUY1 = dluY(1);
	public static final ConstantSize DLUY2 = dluY(2);
	public static final ConstantSize DLUY3 = dluY(3);
	public static final ConstantSize DLUY4 = dluY(4);
	public static final ConstantSize DLUY5 = dluY(5);
	public static final ConstantSize DLUY6 = dluY(6);
	public static final ConstantSize DLUY7 = dluY(7);
	public static final ConstantSize DLUY8 = dluY(8);
	public static final ConstantSize DLUY9 = dluY(9);
	public static final ConstantSize DLUY11 = dluY(11);
	public static final ConstantSize DLUY14 = dluY(14);

	// Static Component Sizes ***********************************************

	/**
	 * Use the maximum of all component minimum sizes as column or row size.
	 */
	public static final ComponentSize MINIMUM = new ComponentSize("minimum");

	/**
	 * Use the maximum of all component preferred sizes as column or row size.
	 */
	public static final ComponentSize PREFERRED = new ComponentSize("preferred");

	/**
	 * Use the maximum of all component sizes as column or row size; measures
	 * preferred sizes when asked for the preferred size and minimum sizes when
	 * asked for the minimum size.
	 */
	public static final ComponentSize DEFAULT = new ComponentSize("default");

	/**
	 * An array of all enumeration values used to canonicalize deserialized
	 * component sizes.
	 */
	private static final ComponentSize[] VALUES = { MINIMUM, PREFERRED, DEFAULT };

	// Singleton State *******************************************************

	/**
	 * Holds the current converter that maps non-pixel sizes to pixels.
	 */
	private static UnitConverter unitConverter;

	// Instance Creation ******************************************************

	private Sizes() {
		// Suppresses default constructor, ensuring non-instantiability.
	}

	// Creation of Size Instances *********************************************

	/**
	 * Creates and returns an instance of <code>ConstantSize</code> from the
	 * given encoded size and unit description.
	 * 
	 * @param encodedValueAndUnit
	 *            value and unit in string representation
	 * @param horizontal
	 *            true for horizontal, false for vertical
	 * @return a <code>ConstantSize</code> for the given value and unit
	 */
	public static ConstantSize constant(String encodedValueAndUnit,
			boolean horizontal) {
		return ConstantSize.valueOf(encodedValueAndUnit, horizontal);
	}

	/**
	 * Creates and returns a ConstantSize for the specified value in horizontal
	 * dialog units.
	 * 
	 * @param value
	 *            size value in horizontal dialog units
	 * @return the associated <code>ConstantSize</code>
	 */
	public static ConstantSize dluX(int value) {
		return ConstantSize.dluX(value);
	}

	/**
	 * Creates and returns a ConstantSize for the specified value in vertical
	 * dialog units.
	 * 
	 * @param value
	 *            size value in vertical dialog units
	 * @return the associated <code>ConstantSize</code>
	 */
	public static ConstantSize dluY(int value) {
		return ConstantSize.dluY(value);
	}

	/**
	 * Creates and returns a ConstantSize for the specified pixel value.
	 * 
	 * @param value
	 *            value in pixel
	 * @return the associated <code>ConstantSize</code>
	 */
	public static ConstantSize pixel(int value) {
		return new ConstantSize(value, ConstantSize.PIXEL);
	}

	/**
	 * Creates and returns a BoundedSize for the given basis using the specified
	 * lower and upper bounds.
	 * 
	 * @param basis
	 *            the base size
	 * @param lowerBound
	 *            the lower bound size
	 * @param upperBound
	 *            the upper bound size
	 * @return a <code>BoundedSize</code> for the given basis and bounds
	 * @throws NullPointerException
	 *             if basis is null
	 */
	public static Size bounded(Size basis, Size lowerBound, Size upperBound) {
		return new BoundedSize(basis, lowerBound, upperBound);
	}

	// Unit Conversion ******************************************************

	/**
	 * Converts Inches and returns pixels using the current resolution.
	 * 
	 * @param in
	 *            the Inches
	 * @return the given Inches as pixels
	 */
	public static int inchAsPixel(double in) {
		return in == 0d ? 0 : getUnitConverter().inchAsPixel(in);
	}

	/**
	 * Converts Millimeters and returns pixels using the current resolution.
	 * 
	 * @param mm
	 *            Millimeters
	 * @return the given Millimeters as pixels
	 */
	public static int millimeterAsPixel(double mm) {
		return mm == 0d ? 0 : getUnitConverter().millimeterAsPixel(mm);
	}

	/**
	 * Converts Centimeters and returns pixels using the current resolution.
	 * 
	 * @param cm
	 *            Centimeters
	 * @return the given Centimeters as pixels
	 */
	public static int centimeterAsPixel(double cm) {
		return cm == 0d ? 0 : getUnitConverter().centimeterAsPixel(cm);
	}

	/**
	 * Converts DTP Points and returns pixels using the current resolution.
	 * 
	 * @param pt
	 *            DTP Points
	 * @return the given Points as pixels
	 */
	public static int pointAsPixel(int pt) {
		return pt == 0 ? 0 : getUnitConverter().pointAsPixel(pt);
	}

	/**
	 * Converts horizontal dialog units and returns pixels. Honors the
	 * resolution, dialog font size, platform, and l&amp;f.
	 * 
	 * @param dluX
	 *            the horizontal dialog units
	 * @param component
	 *            the component that provides the graphics object
	 * @return the given horizontal dialog units as pixels
	 */
	public static int dialogUnitXAsPixel(int dluX, Control component) {
		return dluX == 0 ? 0 : getUnitConverter().dialogUnitXAsPixel(dluX,
				component);
	}

	/**
	 * Converts vertical dialog units and returns pixels. Honors the resolution,
	 * dialog font size, platform, and l&amp;f.
	 * 
	 * @param dluY
	 *            the vertical dialog units
	 * @param component
	 *            the component that provides the graphics object
	 * @return the given vertical dialog units as pixels
	 */
	public static int dialogUnitYAsPixel(int dluY, Control component) {
		return dluY == 0 ? 0 : getUnitConverter().dialogUnitYAsPixel(dluY,
				component);
	}

	// Accessing the Unit Converter *******************************************

	/**
	 * Returns the current {@link UnitConverter}. If it has not been
	 * initialized before it will get an instance of
	 * {@link DefaultUnitConverter}.
	 * 
	 * @return the current <code>UnitConverter</code>
	 */
	public static UnitConverter getUnitConverter() {
		if (unitConverter == null) {
			unitConverter = DefaultUnitConverter.getInstance();
		}
		return unitConverter;
	}

	/**
	 * Sets a new UnitConverter that will be used to convert font-dependent
	 * sizes to pixel sizes.
	 * 
	 * @param newUnitConverter
	 *            the unit converter to be set
	 */
	public static void setUnitConverter(UnitConverter newUnitConverter) {
		unitConverter = newUnitConverter;
	}

	// Helper Class *********************************************************

	/**
	 * An ordinal-based serializable typesafe enumeration that implements the
	 * {@link Size} interface for the component sizes:
	 * <em>min, pref, default</em>.
	 */
	static final class ComponentSize implements Size, Serializable {
		private static final long serialVersionUID = 1L;

		private final transient String name;

		private ComponentSize(String name) {
			this.name = name;
		}

		/**
		 * Returns an instance of <code>ComponentSize</code> that corresponds
		 * to the specified string.
		 * 
		 * @param str
		 *            the encoded component size
		 * @return the corresponding ComponentSize or null if none matches
		 */
		static ComponentSize valueOf(String str) {
			if (str.equals("m") || str.equals("min"))
				return MINIMUM;
			if (str.equals("p") || str.equals("pref"))
				return PREFERRED;
			if (str.equals("d") || str.equals("default"))
				return DEFAULT;
			return null;
		}

		/**
		 * Computes the maximum size for the given list of controls, using this
		 * form spec and the specified measure.
		 * <p>
		 * Invoked by FormLayout to determine the size of one of my elements
		 * 
		 * @return the maximum size for the given list of controls
		 */
		public int maximumSize(Composite composite, List controls,
				FormLayout.Measure minMeasure, FormLayout.Measure prefMeasure,
				FormLayout.Measure defaultMeasure) {

			FormLayout.Measure measure = this == MINIMUM ? minMeasure
					: (this == PREFERRED ? prefMeasure : defaultMeasure);
			int maximum = 0;
			for (Iterator i = controls.iterator(); i.hasNext();) {
				Control c = (Control) i.next();
				maximum = Math.max(maximum, measure.sizeOf(c));
			}
			return maximum;
		}

		public String toString() {
			return name.substring(0, 1);
		}

		// Serialization *****************************************************

		private static int nextOrdinal = 0;

		private final int ordinal = nextOrdinal++;

		private Object readResolve() {
			return VALUES[ordinal]; // Canonicalize
		}

	}

}