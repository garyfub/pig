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
package org.apache.pig.penny.apps.nop;

import java.util.HashMap;
import java.util.Map;

import org.apache.pig.penny.ClassWithArgs;
import org.apache.pig.penny.ParsedPigScript;
import org.apache.pig.penny.PennyServer;

/**
 * Does nothing except add Penny overhead
 */
public class Main {
    
    public static void main(String[] args) throws Exception {
        boolean skipInstrumentation = false;
        for(String a: args) {
            System.out.println(a);
        }
        if (args.length > 1 && args[1].equals("justLaunch")) {
            skipInstrumentation = true;
            System.out.println("Just launching");
        } else {
            System.out.println("Adding instrumentation");
        }
        PennyServer igServer= new PennyServer();
        String pigScriptFilename = args[0];
        ParsedPigScript parsedPigScript = igServer.parse(pigScriptFilename);

        Map<String, ClassWithArgs> monitorClasses = new HashMap<String, ClassWithArgs>();
        if (!skipInstrumentation) {
            for (String alias: parsedPigScript.aliases()) {
                monitorClasses.put(alias, new ClassWithArgs(NOPMonitorAgent.class));
            }
        }
        parsedPigScript.trace(NOPCoordinator.class, monitorClasses);
    }
}
