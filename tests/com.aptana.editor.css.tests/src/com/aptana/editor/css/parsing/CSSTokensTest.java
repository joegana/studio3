/**
 * Aptana Studio
 * Copyright (c) 2005-2011 by Appcelerator, Inc. All Rights Reserved.
 * Licensed under the terms of the GNU Public License (GPL) v3 (with exceptions).
 * Please see the license.html included with this distribution for details.
 * Any modifications to this file must keep this entire header intact.
 */
package com.aptana.editor.css.parsing;

import junit.framework.TestCase;
import beaver.Symbol;

import com.aptana.editor.css.parsing.lexer.CSSTokenType;

public class CSSTokensTest extends TestCase
{
	class TokenInfo
	{
		public final CSSTokenType type;
		public final int offset;
		public final int length;

		public TokenInfo(CSSTokenType type, int offset, int length)
		{
			this.type = type;
			this.offset = offset;
			this.length = length;
		}
	}

	private CSSFlexScanner fScanner;

	@Override
	protected void setUp() throws Exception
	{
		fScanner = new CSSFlexScanner();
	}

	@Override
	protected void tearDown() throws Exception
	{
		fScanner = null;
	}

	protected void assertToken(String source, CSSTokenType type, int offset, int length)
	{
		assertToken(source, new TokenInfo(type, offset, length));
		assertToken(source.toUpperCase(), new TokenInfo(type, offset, length));
	}

	protected void assertToken(String source, TokenInfo... infos)
	{
		setSource(source);
		assertToken(infos);
	}

	protected void assertToken(CSSTokenType type, int offset, int length)
	{
		assertToken(new TokenInfo(type, offset, length));
	}

	protected void assertToken(TokenInfo... infos)
	{
		for (TokenInfo info : infos)
		{
			try
			{
				Symbol token = fScanner.nextToken();

				// Allow null types when we don't need to verify the type, like with WHITESPACE, for example
				if (info.type != null)
				{
					// Add in token types that are being tested, regardless if they pass or not. This info is used by
					// another test to determine if we've covered all token types during testing
					VerifyTestedTokensTest.TESTED_TOKEN_TYPES.add(info.type);

					assertEquals("Checking token type", info.type.getIndex(), token.getId());
				}

				assertEquals("Checking token offset", info.offset, token.getStart());
				assertEquals("Checking token length", info.length, token.getEnd() - token.getStart() + 1);
			}
			catch (Exception t)
			{
				fail(t.getMessage());
			}
		}
	}

	protected void setSource(String source)
	{
		fScanner.setSource(source);
	}
}
