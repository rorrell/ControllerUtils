/*
 * Copyright (c) 2015, Rachel Orrell <rachel.orrell@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     - Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *
 *     - Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *
 *     - Neither the name of Rachel Orrell,  nor the names of its 
 *       contributors may be used to endorse or promote products derived
 *       from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package utils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Parent;

/**
 * Interface for JavaFX controllers allowing passing of certain parameters
 * @author Rachel Orrell
 */
public interface IndexedController {

    /**
     * Primary id
     */
    final IntegerProperty mainIndex = new SimpleIntegerProperty(-1);

    /**
     * Secondary id
     */
    final IntegerProperty subIndex = new SimpleIntegerProperty(-1);

    /**
     * Parent node
     */
    final ObjectProperty<Parent> parent = new SimpleObjectProperty<Parent>(null);

    /**
     * Previous fxml page
     */
    final StringProperty previous = new SimpleStringProperty("");

    /**
     * Custom message
     */
    final StringProperty msg = new SimpleStringProperty("");
    
    /**
     * Set primary id
     * @param i primary id
     */
    default public void setMainIndex(int i) {
        mainIndex.set(i);
    }
    
    /**
     * Get primary id
     * @return int
     */
    default public int getMainIndex() {
        return mainIndex.get();
    }
    
    /**
     * Set secondary id
     * @param i secondary id
     */
    default public void setSubIndex(int i) {
        subIndex.set(i);
    }
    
    /**
     * Get secondary id
     * @return int
     */
    default public int getSubIndex() {
        return subIndex.get();
    }
    
    /**
     * Set parent node
     * @param p parent node object
     */
    default public void setParent(Parent p) {
        parent.setValue(p);
    }
    
    /**
     * Get parent node
     * @return Parent
     */
    default public Parent getParent() {
        return parent.getValue();
    }
    
    /**
     * Get previous fxml page
     * @return String
     */
    default public String getPrevious() {
        return previous.get();
    }
    
    /**
     * Set previous fxml page
     * @param p fxml file name
     */
    default public void setPrevious(String p) {
        previous.set(p);
    }
    
    /**
     * Get custom msg
     * @return String
     */
    default public String getMsg() {
        return msg.get();
    }
    
    /**
     * Set custom msg
     * @param m custom msg
     */
    default public void setMsg(String m) {
        msg.set(m);
    }
    
    /**
     * Optional setup method that, unlike initialize, occurs after the parameters
     * in this interface have been set
     */
    default public void setup() {
    }
}
