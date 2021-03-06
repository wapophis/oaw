/*******************************************************************************
* Copyright (C) 2012 INTECO, Instituto Nacional de Tecnologías de la Comunicación, 
* This program is licensed and may be used, modified and redistributed under the terms
* of the European Public License (EUPL), either version 1.2 or (at your option) any later 
* version as soon as they are approved by the European Commission.
* Unless required by applicable law or agreed to in writing, software distributed under the 
* License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF 
* ANY KIND, either express or implied. See the License for the specific language governing 
* permissions and more details.
* You should have received a copy of the EUPL1.2 license along with this program; if not, 
* you may find it at http://eur-lex.europa.eu/legal-content/EN/TXT/?uri=CELEX:32017D0863
* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
* Modificaciones: MINHAFP (Ministerio de Hacienda y Función Pública) 
* Email: observ.accesibilidad@correo.gob.es
******************************************************************************/
package es.inteco.rastreador2.utils;

import es.inteco.common.Constants;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.ArrayList;

public class MenuTag extends TagSupport {

    private static final long serialVersionUID = 1L;
    private String roles = null;
    private String noRoles = null;

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getNoRoles() {
        return noRoles;
    }

    public void setNoRoles(String noRoles) {
        this.noRoles = noRoles;
    }

    private boolean containsUserRol(String userRoles) {
        ArrayList<String> role = (ArrayList<String>) pageContext.getSession().getAttribute(Constants.ROLE);
        String[] roles = userRoles.split(";");
        boolean hasRol = false;
        for (int i = 0; i < roles.length && !hasRol; i++) {
            if (role.contains(roles[i])) {
                hasRol = true;
            }
        }
        return hasRol;
    }

    public int doStartTag() throws JspException {
        if (this.noRoles != null) {
            if (this.roles != null && containsUserRol(this.roles) && !containsUserRol(this.noRoles)) {
                return EVAL_BODY_INCLUDE;
            }
        } else {
            if (this.roles != null && containsUserRol(this.roles)) {
                return EVAL_BODY_INCLUDE;
            }
        }
        return SKIP_BODY;
    }

    public int doEndTag() throws JspException {
        // Imprime el elemento de cierre.
        JspWriter writer = pageContext.getOut();
        try {
            writer.print("");
        } catch (IOException e) {
            throw new JspException(e);
        }

        return EVAL_PAGE;
    }

}
