/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/arpith/DMUCS/android/DmucsCommu/src/itu/beddernet/approuter/IBeddernetService.aidl
 */
package itu.beddernet.approuter;
/**
 * Example of defining an interface for calling on to a remote service
 * (running in another process).
 */
public interface IBeddernetService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements itu.beddernet.approuter.IBeddernetService
{
private static final java.lang.String DESCRIPTOR = "itu.beddernet.approuter.IBeddernetService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an itu.beddernet.approuter.IBeddernetService interface,
 * generating a proxy if needed.
 */
public static itu.beddernet.approuter.IBeddernetService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof itu.beddernet.approuter.IBeddernetService))) {
return ((itu.beddernet.approuter.IBeddernetService)iin);
}
return new itu.beddernet.approuter.IBeddernetService.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
itu.beddernet.approuter.IBeddernetServiceCallback _arg0;
_arg0 = itu.beddernet.approuter.IBeddernetServiceCallback.Stub.asInterface(data.readStrongBinder());
java.lang.String _arg1;
_arg1 = data.readString();
long _result = this.registerCallback(_arg0, _arg1);
reply.writeNoException();
reply.writeLong(_result);
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
itu.beddernet.approuter.IBeddernetServiceCallback _arg0;
_arg0 = itu.beddernet.approuter.IBeddernetServiceCallback.Stub.asInterface(data.readStrongBinder());
java.lang.String _arg1;
_arg1 = data.readString();
this.unregisterCallback(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_sendUnicast:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
byte[] _arg2;
_arg2 = data.createByteArray();
java.lang.String _arg3;
_arg3 = data.readString();
this.sendUnicast(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_sendMulticast:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String[] _arg0;
_arg0 = data.createStringArray();
java.lang.String _arg1;
_arg1 = data.readString();
byte[] _arg2;
_arg2 = data.createByteArray();
java.lang.String _arg3;
_arg3 = data.readString();
this.sendMulticast(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
return true;
}
case TRANSACTION_getDevices:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String[] _result = this.getDevices(_arg0);
reply.writeNoException();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_getDevicesSupportingUAIS:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String[] _result = this.getDevicesSupportingUAIS(_arg0);
reply.writeNoException();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_getApplicationSupport:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
boolean _result = this.getApplicationSupport(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_setInvocable:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
boolean _arg1;
_arg1 = (0!=data.readInt());
java.lang.String _arg2;
_arg2 = data.readString();
this.setInvocable(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_findNeighbors:
{
data.enforceInterface(DESCRIPTOR);
this.findNeighbors();
reply.writeNoException();
return true;
}
case TRANSACTION_manualConnect:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.manualConnect(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_startMaintainer:
{
data.enforceInterface(DESCRIPTOR);
this.startMaintainer();
reply.writeNoException();
return true;
}
case TRANSACTION_stopMaintainer:
{
data.enforceInterface(DESCRIPTOR);
this.stopMaintainer();
reply.writeNoException();
return true;
}
case TRANSACTION_manualDisconnect:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.manualDisconnect(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setDiscoverable:
{
data.enforceInterface(DESCRIPTOR);
boolean _arg0;
_arg0 = (0!=data.readInt());
this.setDiscoverable(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getNeighbours:
{
data.enforceInterface(DESCRIPTOR);
long _arg0;
_arg0 = data.readLong();
java.lang.String[] _result = this.getNeighbours(_arg0);
reply.writeNoException();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_disableBluetooth:
{
data.enforceInterface(DESCRIPTOR);
this.disableBluetooth();
reply.writeNoException();
return true;
}
case TRANSACTION_getDevicesWithStatus:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String[] _result = this.getDevicesWithStatus();
reply.writeNoException();
reply.writeStringArray(_result);
return true;
}
case TRANSACTION_getAllUAIHOnDevice:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
long[] _result = this.getAllUAIHOnDevice(_arg0);
reply.writeNoException();
reply.writeLongArray(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements itu.beddernet.approuter.IBeddernetService
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Often you want to allow a service to call back to its clients.
     * This shows how to do so, by registering a callback interface with
     * the service.
     */
@Override public long registerCallback(itu.beddernet.approuter.IBeddernetServiceCallback cb, java.lang.String applicationIdentifier) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
_data.writeString(applicationIdentifier);
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
     * Remove a previously registered callback interface.
     */
@Override public void unregisterCallback(itu.beddernet.approuter.IBeddernetServiceCallback cb, java.lang.String applicationIdentifier) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
_data.writeString(applicationIdentifier);
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Send a unicast message.  recipientApplicationIdentifier should be null unless 
	* message should be sent to a different application address .
     */
@Override public void sendUnicast(java.lang.String networkAddress, java.lang.String recipientApplicationIdentifier, byte[] appMessage, java.lang.String applicationIdentifier) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(networkAddress);
_data.writeString(recipientApplicationIdentifier);
_data.writeByteArray(appMessage);
_data.writeString(applicationIdentifier);
mRemote.transact(Stub.TRANSACTION_sendUnicast, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    * Send a multicast message
    * The service can be null. Default service string is used.
    */
@Override public void sendMulticast(java.lang.String[] networkAddress, java.lang.String recipientApplicationIdentifier, byte[] appMessage, java.lang.String applicationIdentifier) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStringArray(networkAddress);
_data.writeString(recipientApplicationIdentifier);
_data.writeByteArray(appMessage);
_data.writeString(applicationIdentifier);
mRemote.transact(Stub.TRANSACTION_sendMulticast, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    * Returns a list of known addresses on the scatternet. 
    */
@Override public java.lang.String[] getDevices(java.lang.String applicationIdentifier) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(applicationIdentifier);
mRemote.transact(Stub.TRANSACTION_getDevices, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/* Returns list of all devices on the scatternet with "applicationIdentifier", 
    * i.e. that have an app that has inserted that ss.
    */
@Override public java.lang.String[] getDevicesSupportingUAIS(java.lang.String applicationIdentifier) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(applicationIdentifier);
mRemote.transact(Stub.TRANSACTION_getDevicesSupportingUAIS, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/** 
    * Check whether this service is supported by a particular device. 
    * If applicationIdentifier is null, the applicationIdentifierHash is used
    */
@Override public boolean getApplicationSupport(java.lang.String deviceAddress, java.lang.String applicationIdentifier) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(deviceAddress);
_data.writeString(applicationIdentifier);
mRemote.transact(Stub.TRANSACTION_getApplicationSupport, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
    * This tells the scatternet whether it should try to wake the application up. this is an optional feature, implementation depends on platform etc. This would be useful e.g. to start up some messaging application or online logger etc.
	* Set as true if the app behind should be invokeable i.e. if you ever run quake the AR remembers, if someone asks the AR "does he have quake" the reply will be "yes but it's trned off" the AR could then send a request to the user, "do you want to run quake now?" need to think about what is spam, what should be allowed etc.
	* default: false
	*/
@Override public void setInvocable(java.lang.String intentURI, boolean invokable, java.lang.String applicationIdentifier) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(intentURI);
_data.writeInt(((invokable)?(1):(0)));
_data.writeString(applicationIdentifier);
mRemote.transact(Stub.TRANSACTION_setInvocable, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    * DEBUG: Forces a device discovery
    *
    */
@Override public void findNeighbors() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_findNeighbors, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    * DEBUG: Manually connects to a device
    *
    */
@Override public void manualConnect(java.lang.String remoteAddress) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(remoteAddress);
mRemote.transact(Stub.TRANSACTION_manualConnect, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    * DEBUG: Starts the Maintainer thread
    *
    */
@Override public void startMaintainer() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_startMaintainer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    * DEBUG: Stops the Maintainer thread
    *
    */
@Override public void stopMaintainer() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopMaintainer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    * DEBUG: Manually disconnects a device
    *
    */
@Override public void manualDisconnect(java.lang.String remoteAddress) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(remoteAddress);
mRemote.transact(Stub.TRANSACTION_manualDisconnect, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    * DEBUG: Manually sets device visible to other bluetooth devices.
    *
    */
@Override public void setDiscoverable(boolean discoverable) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(((discoverable)?(1):(0)));
mRemote.transact(Stub.TRANSACTION_setDiscoverable, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    * DEBUG: gets list of bluetooth neighbours
    *
    */
@Override public java.lang.String[] getNeighbours(long token) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeLong(token);
mRemote.transact(Stub.TRANSACTION_getNeighbours, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
    * DEBUG: disable bluetooth 
    *
    */
@Override public void disableBluetooth() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_disableBluetooth, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
    * DEBUG: Returns a list of known addresses on the scatternet with status
    * M = Master, S = Slave, X= not a bluetooth neighbor
    *
    */
@Override public java.lang.String[] getDevicesWithStatus() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
java.lang.String[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDevicesWithStatus, _data, _reply, 0);
_reply.readException();
_result = _reply.createStringArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	*DEBUG
    *Returns all service hashes on a specific device. 
    */
@Override public long[] getAllUAIHOnDevice(java.lang.String UAIH) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long[] _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(UAIH);
mRemote.transact(Stub.TRANSACTION_getAllUAIHOnDevice, _data, _reply, 0);
_reply.readException();
_result = _reply.createLongArray();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_sendUnicast = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_sendMulticast = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_getDevices = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_getDevicesSupportingUAIS = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getApplicationSupport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setInvocable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_findNeighbors = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_manualConnect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_startMaintainer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_stopMaintainer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_manualDisconnect = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_setDiscoverable = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_getNeighbours = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_disableBluetooth = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_getDevicesWithStatus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_getAllUAIHOnDevice = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
}
/**
     * Often you want to allow a service to call back to its clients.
     * This shows how to do so, by registering a callback interface with
     * the service.
     */
public long registerCallback(itu.beddernet.approuter.IBeddernetServiceCallback cb, java.lang.String applicationIdentifier) throws android.os.RemoteException;
/**
     * Remove a previously registered callback interface.
     */
public void unregisterCallback(itu.beddernet.approuter.IBeddernetServiceCallback cb, java.lang.String applicationIdentifier) throws android.os.RemoteException;
/**
     * Send a unicast message.  recipientApplicationIdentifier should be null unless 
	* message should be sent to a different application address .
     */
public void sendUnicast(java.lang.String networkAddress, java.lang.String recipientApplicationIdentifier, byte[] appMessage, java.lang.String applicationIdentifier) throws android.os.RemoteException;
/**
    * Send a multicast message
    * The service can be null. Default service string is used.
    */
public void sendMulticast(java.lang.String[] networkAddress, java.lang.String recipientApplicationIdentifier, byte[] appMessage, java.lang.String applicationIdentifier) throws android.os.RemoteException;
/**
    * Returns a list of known addresses on the scatternet. 
    */
public java.lang.String[] getDevices(java.lang.String applicationIdentifier) throws android.os.RemoteException;
/* Returns list of all devices on the scatternet with "applicationIdentifier", 
    * i.e. that have an app that has inserted that ss.
    */
public java.lang.String[] getDevicesSupportingUAIS(java.lang.String applicationIdentifier) throws android.os.RemoteException;
/** 
    * Check whether this service is supported by a particular device. 
    * If applicationIdentifier is null, the applicationIdentifierHash is used
    */
public boolean getApplicationSupport(java.lang.String deviceAddress, java.lang.String applicationIdentifier) throws android.os.RemoteException;
/**
    * This tells the scatternet whether it should try to wake the application up. this is an optional feature, implementation depends on platform etc. This would be useful e.g. to start up some messaging application or online logger etc.
	* Set as true if the app behind should be invokeable i.e. if you ever run quake the AR remembers, if someone asks the AR "does he have quake" the reply will be "yes but it's trned off" the AR could then send a request to the user, "do you want to run quake now?" need to think about what is spam, what should be allowed etc.
	* default: false
	*/
public void setInvocable(java.lang.String intentURI, boolean invokable, java.lang.String applicationIdentifier) throws android.os.RemoteException;
/**
    * DEBUG: Forces a device discovery
    *
    */
public void findNeighbors() throws android.os.RemoteException;
/**
    * DEBUG: Manually connects to a device
    *
    */
public void manualConnect(java.lang.String remoteAddress) throws android.os.RemoteException;
/**
    * DEBUG: Starts the Maintainer thread
    *
    */
public void startMaintainer() throws android.os.RemoteException;
/**
    * DEBUG: Stops the Maintainer thread
    *
    */
public void stopMaintainer() throws android.os.RemoteException;
/**
    * DEBUG: Manually disconnects a device
    *
    */
public void manualDisconnect(java.lang.String remoteAddress) throws android.os.RemoteException;
/**
    * DEBUG: Manually sets device visible to other bluetooth devices.
    *
    */
public void setDiscoverable(boolean discoverable) throws android.os.RemoteException;
/**
    * DEBUG: gets list of bluetooth neighbours
    *
    */
public java.lang.String[] getNeighbours(long token) throws android.os.RemoteException;
/**
    * DEBUG: disable bluetooth 
    *
    */
public void disableBluetooth() throws android.os.RemoteException;
/**
    * DEBUG: Returns a list of known addresses on the scatternet with status
    * M = Master, S = Slave, X= not a bluetooth neighbor
    *
    */
public java.lang.String[] getDevicesWithStatus() throws android.os.RemoteException;
/**
	*DEBUG
    *Returns all service hashes on a specific device. 
    */
public long[] getAllUAIHOnDevice(java.lang.String UAIH) throws android.os.RemoteException;
}
