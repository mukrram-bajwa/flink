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

package org.apache.flink.runtime.taskmanager;

import org.apache.flink.runtime.instance.ActorGateway;
import org.apache.flink.runtime.messages.TaskMessages;
import org.apache.flink.util.Preconditions;

/**
 * Implementation using {@link ActorGateway} to forward the messages.
 */
public class ActorGatewayTaskExecutionStateListener implements TaskExecutionStateListener {

	private final ActorGateway actorGateway;

	public ActorGatewayTaskExecutionStateListener(ActorGateway actorGateway) {
		this.actorGateway = Preconditions.checkNotNull(actorGateway);
	}

	@Override
	public void notifyTaskExecutionStateChanged(TaskExecutionState taskExecutionState) {
		TaskMessages.UpdateTaskExecutionState actorMessage = new TaskMessages.UpdateTaskExecutionState(taskExecutionState);

		actorGateway.tell(actorMessage);
	}
}
