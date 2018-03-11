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

package org.apache.flink.runtime.operators.asm;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;

public class DriverMethodAdopter extends MethodVisitor {

	private final String newClassName, oldClassName;

	public DriverMethodAdopter(final MethodVisitor methodVisitor, String newClassName, String oldClassName) {
		super(Opcodes.ASM5, methodVisitor);
		this.newClassName = newClassName;
		this.oldClassName = oldClassName;
	}

	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		if(owner.contains(oldClassName))
			mv.visitFieldInsn(opcode, newClassName, name, desc);
		else
			mv.visitFieldInsn(opcode, owner, name, desc);
	}

	public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
		if(owner.contains(oldClassName))
			mv.visitMethodInsn(opcode, newClassName, name, desc, itf);
		else
			mv.visitMethodInsn(opcode, owner, name, desc, itf);

	}
}
