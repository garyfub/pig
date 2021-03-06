/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.pig.penny;

import java.io.Serializable;

import org.apache.pig.data.Tuple;

public abstract class Coordinator {
    
    // ADDED 10-19-10 BY CHRIS:
    
    private Communicator communicator;

    public void initialize(Communicator communicator) {
        this.communicator = communicator;
    }
    
    protected Communicator communicator() {
        return communicator;
    }
    
    
    
    ///// Abstract methods that app-writer implements:
    
    /**
     * Initialize, using any arguments passed from higher layer.
     */
    public abstract void init(Serializable[] args);
        
    /**
     * Process an incoming (synchronous or asynchronous) message.
     */
    public abstract void receiveMessage(Location source, Tuple message);

    /**
     * The data flow has completed and all messages have been delivered. Finish processing.
     * 
     * @return        final output to pass back to application
     */
    public abstract Object finish();

}
