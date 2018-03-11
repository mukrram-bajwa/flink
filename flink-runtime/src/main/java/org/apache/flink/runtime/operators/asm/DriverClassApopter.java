package org.apache.flink.runtime.operators.asm;

import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.internal.org.objectweb.asm.Opcodes;
/*
	Class responsible for the transformations for the Drivers.
 */
public class DriverClassApopter extends ClassVisitor{

	String newClassName, oldClassName;

	public DriverClassApopter(String newClassName, String oldClassName, ClassVisitor classVisitor) {
		super(Opcodes.ASM5, classVisitor);
		this.newClassName = newClassName;
		this.oldClassName = oldClassName;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		cv.visit(version, access, newClassName, signature, superName, interfaces);
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		// In ASM, renaming of a class requires method level transformation, because methods has link in the binary to its owner class.
		MethodVisitor methodVisitor = cv.visitMethod(access, name, desc, signature, exceptions);
		if (methodVisitor != null && (access & Opcodes.ACC_ABSTRACT) == 0)
			methodVisitor = new DriverMethodAdopter(methodVisitor, newClassName, oldClassName);  // This method requires transformation as it possibly has link to the owner class.
		return methodVisitor;
	}
}
