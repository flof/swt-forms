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

package net.ffxml.swtforms.factories;

import java.util.StringTokenizer;

import net.ffxml.swtforms.layout.Border;
import net.ffxml.swtforms.layout.ConstantSize;
import net.ffxml.swtforms.layout.Sizes;
import net.ffxml.swtforms.util.LayoutStyle;

/**
 * Provides constants and factory methods for <code>Border</code>s that use
 * instances of {@link ConstantSize} to define the margins.
 * <p>
 * 
 * <strong>Examples:</strong><br>
 * 
 * <pre>
 * Borders.DLU2_BORDER
 * Borders.createEmptyBorder(Sizes.DLUY4, Sizes.DLUX2, Sizes.DLUY4, Sizes.DLUX2);
 * Borders.createEmptyBorder(&quot;4dlu, 2dlu, 4dlu, 2dlu&quot;);
 * </pre>
 * 
 * @author Karsten Lentzsch
 * @version $Revision: 1.2 $
 * @see Border
 * @see Sizes
 */
public final class Borders {

	private Borders() {
		// Overrides default constructor; prevents instantiation.
	}

	// Constant Borders *****************************************************

	/**
	 * A prepared and reusable EmptyBorder without gaps.
	 */
	public static final Border EMPTY_BORDER = new Border(0, 0, 0, 0);

	/**
	 * A prepared and reusable Border with 2dlu on all sides.
	 */
	public static final Border DLU2_BORDER = createEmptyBorder(Sizes.DLUY2,
			Sizes.DLUX2, Sizes.DLUY2, Sizes.DLUX2);

	/**
	 * A prepared and reusable Border with 4dlu on all sides.
	 */
	public static final Border DLU4_BORDER = createEmptyBorder(Sizes.DLUY4,
			Sizes.DLUX4, Sizes.DLUY4, Sizes.DLUX4);

	/**
	 * A prepared and reusable Border with 7dlu on all sides.
	 */
	public static final Border DLU7_BORDER = createEmptyBorder(Sizes.DLUY7,
			Sizes.DLUX7, Sizes.DLUY7, Sizes.DLUX7);

	/**
	 * A prepared Border with 14dlu on all sides.
	 */
	public static final Border DLU14_BORDER = createEmptyBorder(Sizes.DLUY14,
			Sizes.DLUX14, Sizes.DLUY14, Sizes.DLUX14);

	/**
	 * A standardized Border that describes the gap between a component and a
	 * button bar in its bottom.
	 */
	public static final Border BUTTON_BAR_GAP_BORDER = createEmptyBorder(
			LayoutStyle.getCurrent().getButtonBarPad(), Sizes.dluX(0), Sizes
					.dluY(0), Sizes.dluX(0));

	/**
	 * A standardized Border that describes the border around a dialog content
	 * that has no tabs.
	 * 
	 * @see #TABBED_DIALOG_BORDER
	 */
	public static final Border DIALOG_BORDER = createEmptyBorder(LayoutStyle
			.getCurrent().getDialogMarginY(), LayoutStyle.getCurrent()
			.getDialogMarginX(), LayoutStyle.getCurrent().getDialogMarginY(),
			LayoutStyle.getCurrent().getDialogMarginX());

	/**
	 * A standardized Border that describes the border around a dialog content
	 * that uses tabs.
	 * 
	 * @see #DIALOG_BORDER
	 */
	public static final Border TABBED_DIALOG_BORDER = createEmptyBorder(
			LayoutStyle.getCurrent().getTabbedDialogMarginY(), LayoutStyle
					.getCurrent().getTabbedDialogMarginX(), LayoutStyle
					.getCurrent().getTabbedDialogMarginY(), LayoutStyle
					.getCurrent().getTabbedDialogMarginX());

	// Factory Methods ******************************************************

	/**
	 * Creates and returns an <code>EmptyBorder</code> with the specified
	 * gaps.
	 * 
	 * @param top
	 *            the top gap
	 * @param left
	 *            the left-hand side gap
	 * @param bottom
	 *            the bottom gap
	 * @param right
	 *            the right-hand side gap
	 * @return an <code>EmptyBorder</code> with the specified gaps
	 * 
	 * @see #createEmptyBorder(String)
	 */
	public static Border createEmptyBorder(ConstantSize top, ConstantSize left,
			ConstantSize bottom, ConstantSize right) {
		return new Border(top.getPixelSize(null), left.getPixelSize(null),
				bottom.getPixelSize(null), right.getPixelSize(null));
	}

	/**
	 * Creates and returns a <code>Border</code> using sizes as specified by
	 * the given string. This string is a comma-separated encoding of 4
	 * <code>ConstantSize</code>s.
	 * 
	 * @param encodedSizes
	 *            top, left, bottom, right gap encoded as String
	 * @return an <code>EmptyBorder</code> with the specified gaps
	 * 
	 * @see #createEmptyBorder(ConstantSize, ConstantSize, ConstantSize,
	 *      ConstantSize)
	 */
	public static Border createEmptyBorder(String encodedSizes) {
		StringTokenizer tokenizer = new StringTokenizer(encodedSizes, ", ");
		int tokenCount = tokenizer.countTokens();
		if (tokenCount != 4) {
			throw new IllegalArgumentException(
					"The border requires 4 sizes, but '" + encodedSizes
							+ "' has " + tokenCount + ".");
		}
		ConstantSize top = Sizes.constant(tokenizer.nextToken(), false);
		ConstantSize left = Sizes.constant(tokenizer.nextToken(), true);
		ConstantSize bottom = Sizes.constant(tokenizer.nextToken(), false);
		ConstantSize right = Sizes.constant(tokenizer.nextToken(), true);
		return createEmptyBorder(top, left, bottom, right);
	}
}
