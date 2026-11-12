/*
 * AllBinary Open License Version 1
 * Copyright (c) 2026 AllBinary
 *
 * By agreeing to this license you and any business entity you represent are
 * legally bound to the AllBinary Open License Version 1 legal agreement.
 *
 * You may obtain the AllBinary Open License Version 1 legal agreement from
 * AllBinary or the root directory of AllBinary's AllBinary Platform repository.
 *
 * Created By: Travis Berthelot
 *
 */
package org.eclipse.swt.browser;

import org.allbinary.logic.string.StringUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.widgets.Composite;

/**
 * Stub of the XULRunner (SWT.MOZILLA) browser for the languages without XPCOM (Go).
 * Creating it fails the same way BrowserFactory fails for an unsupported browser type, so the Browser uses IE.
 */
class Mozilla extends WebBrowser {

    private static final String TYPE = "mozilla"; //$NON-NLS-1$
    private static final String UNSUPPORTED = "Unsupported Browser type"; //$NON-NLS-1$

    @Override
    public void create(final Composite parent, final int style) {
        throw new SWTError(SWT.ERROR_NO_HANDLES, UNSUPPORTED);
    }

    @Override
    public boolean back() {
        return false;
    }

    @Override
    public boolean execute(final String script) {
        return false;
    }

    @Override
    public boolean forward() {
        return false;
    }

    @Override
    public String getBrowserType() {
        return TYPE;
    }

    @Override
    public String getText() {
        return StringUtil.getInstance().EMPTY_STRING;
    }

    @Override
    public String getUrl() {
        return StringUtil.getInstance().EMPTY_STRING;
    }

    @Override
    public boolean isBackEnabled() {
        return false;
    }

    @Override
    public boolean isForwardEnabled() {
        return false;
    }

    @Override
    public void refresh() {
    }

    @Override
    public boolean setText(final String html, final boolean trusted) {
        return false;
    }

    @Override
    public boolean setUrl(final String url, final String postData, final String[] headers) {
        return false;
    }

    @Override
    public void stop() {
    }
}
