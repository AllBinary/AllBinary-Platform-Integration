/**
 *  MicroEmulator
 *  Copyright (C) 2001-2010 Bartek Teodorczyk <barteo@barteo.net>
 *
 *  It is licensed under the following two licenses as alternatives:
 *    1. GNU Lesser General Public License (the "LGPL") version 2.1 or any newer version
 *    2. Apache License (the "AL") Version 2.0
 *
 *  You may not use this file except in compliance with at least one of
 *  the above two licenses.
 *
 *  You may obtain a copy of the LGPL at
 *      http://www.gnu.org/licenses/old-licenses/lgpl-2.1.txt
 *
 *  You may obtain a copy of the AL at
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the LGPL or the AL for the specific language governing permissions and
 *  limitations.
 *
 *  @version $Id: PatternMethodAdapter.java 1784 2008-10-20 14:22:57Z barteo $
 */

package org.microemu.android.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;

public abstract class PatternMethodAdapter extends MethodAdapter {

	public PatternMethodAdapter(MethodVisitor mv) {
		super(mv);
	}
	
	protected abstract void visitInsn();

	@Override
	public void visitFieldInsn(int opcode, String owner, String name, String desc) {
		this.visitInsn();
		super.visitFieldInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitIincInsn(int var, int increment) {
		this.visitInsn();
		super.visitIincInsn(var, increment);
	}

	@Override
	public void visitInsn(int opcode) {
		this.visitInsn();
		super.visitInsn(opcode);
	}

	@Override
	public void visitIntInsn(int opcode, int operand) {
		this.visitInsn();
		super.visitIntInsn(opcode, operand);
	}

	@Override
	public void visitJumpInsn(int opcode, Label label) {
		this.visitInsn();
		super.visitJumpInsn(opcode, label);
	}

	@Override
	public void visitLdcInsn(Object cst) {
		this.visitInsn();
		super.visitLdcInsn(cst);
	}

	@Override
	public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
		this.visitInsn();
		super.visitLookupSwitchInsn(dflt, keys, labels);
	}

	@Override
	public void visitMethodInsn(int opcode, String owner, String name, String desc) {
		this.visitInsn();
		super.visitMethodInsn(opcode, owner, name, desc);
	}

	@Override
	public void visitMultiANewArrayInsn(String desc, int dims) {
		this.visitInsn();
		super.visitMultiANewArrayInsn(desc, dims);
	}

	@Override
	public void visitTableSwitchInsn(int min, int max, Label dflt, Label[] labels) {
		this.visitInsn();
		super.visitTableSwitchInsn(min, max, dflt, labels);
	}

	@Override
	public void visitTypeInsn(int opcode, String type) {
		this.visitInsn();
		super.visitTypeInsn(opcode, type);
	}

	@Override
	public void visitVarInsn(int opcode, int var) {
		this.visitInsn();
		super.visitVarInsn(opcode, var);
	}

}
