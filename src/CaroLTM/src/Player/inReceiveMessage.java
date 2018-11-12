/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Player;

import Model.KMessage;
import java.io.IOException;

/**
 *
 * @author Nam Lee
 */
public interface inReceiveMessage {
    
    public void ReceiveMessage(KMessage msg) throws IOException;
}
