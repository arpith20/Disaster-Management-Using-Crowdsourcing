/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/arpith/DMUCS/android/DmucsCommu/src/itu/beddernet/approuter/IBeddernetServiceCallback.aidl
 */
package itu.beddernet.approuter;
/**
 * Example of a callback interface used by IRemoteService to send
 * synchronous notifications back to its clients.  Note that this is a
 * one-way interface so the server does not block waiting for the client.
 */
public interface IBeddernetServiceCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements itu.beddernet.approuter.IBeddernetServiceCallback
{
private static final java.lang.String DESCRIPTOR = "itu.beddernet.approuter.IBeddernetServiceCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an itu.beddernet.approuter.IBeddernetServiceCallback interface,
 * generating a proxy if needed.
 */
public static itu.beddernet.approuter.IBeddernetServiceCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof itu.beddernet.approuter.IBeddernetServiceCallback))) {
return ((itu.beddernet.approuter.IBeddernetServiceCallback)iin);
}
return new itu.beddernet.approuter.IBeddernetServiceCallback.Stub.Proxy(obj);
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
case TRANSACTION_update:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
byte[] _arg1;
_arg1 = data.createByteArray();
this.update(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_updateWithSendersApplicationIdentifierHash:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
long _arg1;
_arg1 = data.readLong();
byte[] _arg2;
_arg2 = data.createByteArray();
this.updateWithSendersApplicationIdentifierHash(_arg0, _arg1, _arg2);
reply.writeNoException();
return true;
}
case TRANSACTION_getApplicationIdentifierHash:
{
data.enforceInterface(DESCRIPTOR);
long _result = this.getApplicationIdentifierHash();
reply.writeNoException();
reply.writeLong(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements itu.beddernet.approuter.IBeddernetServiceCallback
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
     * Called when the service has a new message for the binding app.
     */
@Override public void update(java.lang.String senderAddress, byte[] message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(senderAddress);
_data.writeByteArray(message);
mRemote.transact(Stub.TRANSACTION_update, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Called when the service has a new message for the binding app.
     */
@Override public void updateWithSendersApplicationIdentifierHash(java.lang.String senderAddress, long senderApplicationIdentifierHash, byte[] message) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(senderAddress);
_data.writeLong(senderApplicationIdentifierHash);
_data.writeByteArray(message);
mRemote.transact(Stub.TRANSACTION_updateWithSendersApplicationIdentifierHash, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public long getApplicationIdentifierHash() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
long _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getApplicationIdentifierHash, _data, _reply, 0);
_reply.readException();
_result = _reply.readLong();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_update = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_updateWithSendersApplicationIdentifierHash = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_getApplicationIdentifierHash = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
}
/**
     * Called when the service has a new message for the binding app.
     */
public void update(java.lang.String senderAddress, byte[] message) throws android.os.RemoteException;
/**
     * Called when the service has a new message for the binding app.
     */
public void updateWithSendersApplicationIdentifierHash(java.lang.String senderAddress, long senderApplicationIdentifierHash, byte[] message) throws android.os.RemoteException;
public long getApplicationIdentifierHash() throws android.os.RemoteException;
}
