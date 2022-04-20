/*
 * semanticcms-core-style - Default style for Java API for modeling web page content and relationships.
 * Copyright (C) 2016, 2017, 2020, 2021, 2022  AO Industries, Inc.
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
 * along with semanticcms-core-style.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.semanticcms.core.style;

import com.aoapps.web.resources.registry.Group;
import com.aoapps.web.resources.registry.Style;
import com.aoapps.web.resources.servlet.RegistryEE;
import com.semanticcms.core.model.Node;
import com.semanticcms.core.model.Page;
import com.semanticcms.core.renderer.html.HtmlRenderer;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener("Registers the styles for SemanticCMS Core in RegistryEE and HtmlRenderer.")
public class CoreStyle implements ServletContextListener {

  public static final Group.Name RESOURCE_GROUP = new Group.Name("semanticcms-core-style");

  // TODO: Change to Group.Name once we have group-level ordering
  public static final Style SEMANTICCMS_CORE = new Style("/semanticcms-core-style/semanticcms-core.css");

  @Override
  public void contextInitialized(ServletContextEvent event) {
    ServletContext servletContext = event.getServletContext();

    // Add our CSS file
    RegistryEE.Application.get(servletContext)
      .activate(RESOURCE_GROUP) // TODO: Activate as-needed
      .getGroup(RESOURCE_GROUP)
      .styles
      .add(SEMANTICCMS_CORE);

    HtmlRenderer htmlRenderer = HtmlRenderer.getInstance(servletContext);
    // Default list item style for nodes otherwise not provided
    htmlRenderer.addListItemCssClass(Node.class, "semanticcms-core-model-list-item-node");
    // Add page list item style
    htmlRenderer.addListItemCssClassResolver(
      Page.class,
      page -> page.getChildRefs().isEmpty()
        ? "semanticcms-core-model-list-item-page-nochildren"
        : "semanticcms-core-model-list-item-page-children"
    );
  }

  @Override
  public void contextDestroyed(ServletContextEvent event) {
    // Do nothing
  }
}
