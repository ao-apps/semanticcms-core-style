/*
 * semanticcms-core-style - Default style for Java API for modeling web page content and relationships.
 * Copyright (C) 2016  AO Industries, Inc.
 *     support@aoindustries.com
 *     7262 Bull Pen Cir
 *     Mobile, AL 36695
 *
 * This file is part of semanticcms-core-style.
 *
 * semanticcms-core-style is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * semanticcms-core-style is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with semanticcms-core-style.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.semanticcms.core.style;

import com.semanticcms.core.model.Node;
import com.semanticcms.core.model.Page;
import com.semanticcms.core.servlet.SemanticCMS;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener("Registers the styles for sections in SemanticCMS.")
public class CoreStyleContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent event) {
		SemanticCMS semanticCMS = SemanticCMS.getInstance(event.getServletContext());
		// Add our CSS file
		semanticCMS.addCssLink("/semanticcms-core-style/styles.css");
		// Default list item style for nodes otherwise not provided
		semanticCMS.addListItemCssClass(Node.class, "semanticcms-core-model-list-item-node");
		// Add page list item style
		semanticCMS.addListItemCssClassResolver(
			Page.class,
			new SemanticCMS.ListItemCssClassResolver<Page>() {
				@Override
				public String getListItemCssClass(Page page) {
					return
						page.getChildPages().isEmpty()
						? "semanticcms-core-model-list-item-page-nochildren"
						: "semanticcms-core-model-list-item-page-children"
					;
				}
			}
		);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// Do nothing
	}
}
