package pm2.common.application;

/*-
 * #%L
 * ATB Context Extraction Core Lib
 * %%
 * Copyright (C) 2020 ATB – Institut für angewandte Systemtechnik Bremen GmbH
 * %%
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * #L%
 */
import pm2.common.io.*;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.HashSet;

import static pm2.common.application.ObjectKinds.*;

public class SysCallerImpl implements SysCaller {

	private int handleCounter;
	private boolean failAccess; // can be used to generate failures for caller testing
	private String sLastError;

	private HashMap<String, OpenObject> openObjects;
    private HashMap<String, ProtectedObject> protectedObjects;

    private POKindCoreServiceMonitoringConfAMs poki1;			// not nice yet
    private POKindCoreServicesConfAMs poki2;			// not nice yet
    private POKindCoreServiceAppScenConfAMs poki3;	// not nice yet

	public SysCallerImpl(boolean maketofail) {
		handleCounter = 0;
		failAccess = maketofail;
		sLastError = null;
		try {
			protectedObjects = new HashMap<>();
			initializeProtectedObjects();
			openObjects = new HashMap<>();
			poki1 = new POKindCoreServiceMonitoringConfAMsImpl();			// not nice yet
			poki2 = new POKindCoreServicesConfAMsImpl();			// not nice yet
			poki3 = new POKindCoreServiceAppScenConfAMsImpl();	// not nice yet
		} catch (Exception e) {
			System.exit(-1);
		}
	}

	private String makeNewHandle() {
		handleCounter = handleCounter + 1;
		return ("ObjHandle_" + Integer.toString(handleCounter));
	}

	class ProtectedObject {
		String poName; // also the key for protectedObjects
		HashSet<String> poPerms;
		ObjectKinds poKind;
		String poLocNode;
		String poLocPath;

		ProtectedObject(String name, String perms, ObjectKinds kind,
                        String node, String path) {
			poName = name;
			poPerms = stringToSet(perms);
			poKind = kind;
			poLocNode = node;
			poLocPath = path;
		}

		public String getName() {
			return poName;
		}

		public HashSet getPerms() {
			return poPerms;
		} // make setToString

		public ObjectKinds getKind() {
			return poKind;
		}

		public String getNode() {
			return poLocNode;
		}

		public String getPath() {
			return poLocPath;
		}

		public void setName(String name) {
			poName = name;
		}

		public void setPerms(String perms) {
			poPerms = stringToSet(perms);
		}

		public void setKind(ObjectKinds kind) {
			poKind = kind;
		}

		public void setNode(String node) {
			poLocNode = node;
		}

		public void setPath(String path) {
			poLocPath = path;
		}
	}

	// initialize protectedObjects from a static array
	static class POinitial {
		String poname;
		String poperms;
		ObjectKinds pokind;
		String ponode;
		String popath;
	};

	// DEFINE ALL PROTECTED OBJECTS HERE
	private static final POinitial[] poi = {
		new POinitial() {
			{
				poname = "services-config.xml";
				poperms = "read";
				pokind = POKind_CoreServices_conf;
				ponode = "node";
				popath = "resources";
			}
		}, new POinitial() {
			{
					poname = "monitoring-config.xml";
					poperms = "all";
					pokind = POKind_CoreService_Monitoring_conf;
					ponode = "node";
					popath = "resources";
			}
		}, new POinitial() {
			{
				poname = "appscenario-config.xml";						// <-- the filename is currently not fixed? How to handle?
				poperms = "all";
				pokind = POKind_CoreService_AppScenConf;
				ponode = "node";
				popath = "resources";
			}
		}, new POinitial() {
			{
				poname = "PO2";
				poperms = "all";
				pokind = POKind_X;
				ponode = "node";
				popath = "path";
			}
		}
	};
	
	private void initializeProtectedObjects() {
        for (POinitial aPoi : poi) {
            ProtectedObject po;
            po = new ProtectedObject(aPoi.poname, aPoi.poperms,
                    aPoi.pokind, aPoi.ponode, aPoi.popath);
            protectedObjects.put(aPoi.poname, po);
        }
	}

	class OpenObject {
		String ooName; // The virtual object name.
		String ooHandle; // the open object handle (also the key)
		ObjectKinds ooKind; // the kind of the open object
		int ooItemPos; // Next item to be read from Content.
		int ooBytePos; // Next byte to be read from the item.
		HashSet ooPerms; // The permissions defined by the policy engine.

		OpenObject(String name, String handle, ObjectKinds kind) {
			ooName = name;
			ooHandle = handle;
			ooKind = kind;
			ooItemPos = 0;
			ooBytePos = 0;
			ooPerms = null;
		}

		public String getName() {
			return ooName;
		}

		public String getHandle() {
			return ooHandle;
		}

		public ObjectKinds getKind() {
			return ooKind;
		}

		public HashSet getPerms() {
			return ooPerms;
		}

		public int getItemPos() {
			return ooItemPos;
		}

		public int getBytePos() {
			return ooBytePos;
		}

		public void setName(String name) {
			ooName = name;
		}

		public void setHandle(String handle) {
			ooHandle = handle;
		}

		public void setKind(ObjectKinds kind) {
			ooKind = kind;
		}

		public void setPerms(String perms) {
			ooPerms = stringToSet(perms);
		}

		public void setItemPos(int pos) {
			ooItemPos = pos;
		}

		public void setBytePos(int pos) {
			ooBytePos = pos;
		}
	}

	public String getLastError() {
		return sLastError;
	}

	public String openDRMobject(String name, String perms) {
		// perms currently ignored
		sLastError = null;
		try {
			ProtectedObject po = protectedObjects.get(name);
			if (po == null) {
				sLastError = "Unknown protected object.";
				return null;
			}
			ObjectKinds kind = po.getKind();
			String handle = makeNewHandle();
			OpenObject oo = new OpenObject(name, handle, kind);
			openObjects.put(handle, oo);
			if (!openResourceAccessMethod(name, kind)) {
				return null;
			}
			return handle;
		} catch (Exception e) {
			sLastError = "Exception in openObject: " + e.getMessage();
			return null;
		}
	}

	public boolean closeDRMobject(String handle) {
		sLastError = null;
		try {
			OpenObject oo = openObjects.get(handle);
			if (oo == null) {
				sLastError = "Invalid handle in closeObject().";
				return false;
			}
			ObjectKinds kind = oo.getKind();
			String name = oo.getName();
			openObjects.remove(handle);
			return closeResourceAccessMethod(name, kind);
		} catch (Exception e) {
			sLastError = "Exception in closeObject: " + e.getMessage();
			return false;
		}
	}

	public byte[] readDRMobject(String handle) {
		sLastError = null;
		try {
			OpenObject oo = openObjects.get(handle);
			if (oo == null) {
				sLastError = "Invalid handle in readObject().";
				return null;
			}
			ObjectKinds kind = oo.getKind();
			String name = oo.getName();
			int itemPos = oo.getItemPos();
			int bytePos = oo.getBytePos();
			return readResourceAccessMethod(name, kind, itemPos, bytePos);
		} catch (Exception e) {
			sLastError = "Exception in readObject(): " + e.getMessage();
			return null;
		}
	}

	public byte[] readDRMobject2(String handle, int count, int offset) {
		sLastError = null;
		try {
			OpenObject oo = openObjects.get(handle);
			if (oo == null) {
				sLastError = "Invalid handle in readObject2().";
				return null;
			}
			ObjectKinds kind = oo.getKind();
			String name = oo.getName();
			return readResourceAccessMethod(name, kind, count, offset);
		} catch (Exception e) {
			sLastError = "Exception in readObject2: " + e.getMessage();
			return null;
		}
	}

	public boolean writeDRMobject(String sHandle, byte[] buf) {
		sLastError = null;
		try {
			OpenObject oo = openObjects.get(sHandle);
			if (oo == null) {
				sLastError = "Invalid handle in writeObject().";
				return false;
			}
			ObjectKinds kind = oo.getKind();
			String name = oo.getName();
			writeResourceAccessMethod(name, kind, buf);
			// update ooItemPos and ooBytePos
			return true;
		} catch (Exception e) {
			sLastError = "Exception in writeObject: " + e.getMessage();
			return false;
		}
	}

	public byte[] getDRMobject(String name) {
		try {
			ProtectedObject po = protectedObjects.get(name);
			if (po == null) {
				sLastError = "Unknown protected object.";
				return null;
			}
			ObjectKinds kind = po.getKind();
			openResourceAccessMethod(name, kind);
			byte[] buf = readResourceAccessMethod(name, kind, 0, 0);
			closeResourceAccessMethod(name, kind);
			return buf;
		} catch (Exception e) {
			sLastError = "Exception in getObject: " + e.getMessage();
			return null;
		}
	}

	public boolean putDRMobject(String name, byte[] buf) {
		try {
			ProtectedObject po = protectedObjects.get(name);
			if (po == null) {
				sLastError = "Unknown protected object.";
				return false;
			}
			ObjectKinds kind = po.getKind();
			openResourceAccessMethod(name, kind);
			writeResourceAccessMethod(name, kind, buf);
			closeResourceAccessMethod(name, kind);
			return true;
		} catch (Exception e) {
			sLastError = "Exception in putObject: " + e.getMessage();
			return false;
		}
	}

	private HashSet<String> stringToSet(String sArg) {
		HashSet<String> set = new HashSet<>();
		if (sArg != null) {
			String[] pieces = sArg.split(",");
            for (String piece : pieces) {
                String t = piece.trim();
                if (t.length() > 0) {
                    set.add(t);
                }
            }
		}
		return set;
	}

	// access methods for the resources

	private boolean openResourceAccessMethod(String name, ObjectKinds kind) {
		ProtectedObject po = protectedObjects.get(name);
		if (po == null) {
			sLastError = "Unknown protected object.";
			return false;
		}
		switch (kind) {
		case POKind_CoreService_Monitoring_conf:
			System.out.println("Calling POKindCoreServiceMonitoringConfOpenAM ...");
			return poki1.POKindCoreServiceConfOpenAM(name, po.getPath(), kind);
		case POKind_CoreServices_conf:
			System.out.println("Calling POKindCoreServicesConfOpenAM ...");
			return poki2.POKindCoreServicesConfOpenAM(name, po.getPath(), kind);
		case POKind_CoreService_AppScenConf:
			System.out.println("Calling POKindCoreServiceAppScenConfOpenAM ...");
			return poki3.POKindCoreServiceAppScenConfOpenAM(name, po.getPath(), kind);
		case POKind_X:
			System.out.println("Calling OpenAM for POKind_X.");
			return false;
		}
		return true;
	}

	private boolean closeResourceAccessMethod(String name, ObjectKinds kind) {
		ProtectedObject po = protectedObjects.get(name);
		if (po == null) {
			sLastError = "Unknown protected object.";
			return false;
		}
		switch (kind) {
		case POKind_CoreService_Monitoring_conf:
			System.out.println("Calling POKindCoreServiceMonitoringConfCloseAM ...");
			return poki1.POKindCoreServiceConfCloseAM(name,kind);
		case POKind_CoreServices_conf:
			System.out.println("Calling POKindCoreServicesConfCloseAM ...");
			return poki2.POKindCoreServicesConfCloseAM(name,kind);
		case POKind_CoreService_AppScenConf:
			System.out.println("Calling POKindCoreServiceAppScenConfCloseAM ...");
			return poki3.POKindCoreServiceAppScenConfCloseAM(name, kind);
		case POKind_X:
			System.out.println("Calling CloseAM for POKind_X.");
			return false;
		}
		return true;
	}

	private byte[] readResourceAccessMethod(String name, ObjectKinds kind, int count,
                                            int offset) {
		ProtectedObject po = protectedObjects.get(name);
		if (po == null) {
			sLastError = "Unknown protected object.";
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		switch (kind) {
		case POKind_CoreService_Monitoring_conf:
			System.out.println("Calling POKindCoreServiceMonitoringConfReadAM ...");
			return poki1.POKindCoreServiceConfReadAM(name, po.getPath(), kind, count, offset);
		case POKind_CoreServices_conf:
			System.out.println("Calling POKindCoreServicesConfReadAM ...");
			return poki2.POKindCoreServicesConfReadAM(name, po.getPath(), kind, count, offset);
		case POKind_CoreService_AppScenConf:
			System.out.println("Calling POKindCoreServiceAppScenConfReadAM ...");
			return poki3.POKindCoreServiceAppScenConfReadAM(name, po.getPath(), kind, count, offset);
		case POKind_X:
			System.out.println("Calling ReadAM for POKind_X.");
			return null;
		}
		return baos.toByteArray();
	}

	private boolean writeResourceAccessMethod(String name, ObjectKinds kind, byte[] buf) {
		ProtectedObject po = protectedObjects.get(name);
		if (po == null) {
			sLastError = "Unknown protected object.";
			return false;
		}
		switch (kind) {
		case POKind_CoreService_Monitoring_conf:
			System.out.println("Calling POKindCoreServiceMonitoringConfWriteAM ...");
			System.out.println("---not supported---");
			break;
		case POKind_CoreServices_conf:
			System.out.println("Calling POKindCoreServicesConfWriteAM ...");
			System.out.println("---not supported---");
			break;
		case POKind_CoreService_AppScenConf:
			System.out.println("Calling POKindCoreServiceAppScenConfWriteAM ...");
			return poki3.POKindCoreServiceAppScenConfWriteAM(name, kind, buf);
		case POKind_X:
			System.out.println("Calling WriteAM for POKind_X.");
			return false;
		}
		return true;
	}
}
