package com.pubnub.api;

import java.io.UnsupportedEncodingException;
import org.bouncycastle.util.SecureRandom;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.pubnub.api.PubnubError.*;

/**
 * Pubnub object facilitates querying channels for messages and listening on
 * channels for presence/message events
 *
 * @author Pubnub
 *
 */

public class Pubnub extends PubnubCore {

    /**
     * Pubnub Constructor
     *
     * @param publish_key
     *            Publish Key
     * @param subscribe_key
     *            Subscribe Key
     * @param secret_key
     *            Secret Key
     * @param cipher_key
     *            Cipher Key
     * @param ssl_on
     *            SSL on ?
     */
    public Pubnub(String publish_key, String subscribe_key, String secret_key,
            String cipher_key, boolean ssl_on) {
        super(publish_key, subscribe_key, secret_key, cipher_key, ssl_on);
    }

    /**
     * Pubnub Constructor
     *
     * @param publish_key
     *            Publish key
     * @param subscribe_key
     *            Subscribe Key
     * @param secret_key
     *            Secret Key
     * @param ssl_on
     *            SSL on ?
     */
    public Pubnub(String publish_key, String subscribe_key, String secret_key,
            boolean ssl_on) {
        super(publish_key, subscribe_key, secret_key, "", ssl_on);
    }

    /**
     * Pubnub Constructor
     *
     * @param publish_key
     *            Publish Key
     * @param subscribe_key
     *            Subscribe Key
     */
    public Pubnub(String publish_key, String subscribe_key) {
        super(publish_key, subscribe_key, "", "", false);
    }

    /**
     * @param publish_key
     *            Publish Key
     * @param subscribe_key
     *            Subscribe Key
     * @param ssl
     */
    public Pubnub(String publish_key, String subscribe_key, boolean ssl) {
        super(publish_key, subscribe_key, "", "", ssl);
    }

    /**
     * @param publish_key
     * @param subscribe_key
     * @param secret_key
     */
    public Pubnub(String publish_key, String subscribe_key, String secret_key) {
        super(publish_key, subscribe_key, secret_key, "", false);
    }

    /**
     * Sets value for UUID
     *
     * @param uuid
     *            UUID value for Pubnub client
     */
    public void setUUID(UUID uuid) {
        this.UUID = uuid.toString();
    }

    protected String uuid() {

        String valueBeforeMD5;
        String valueAfterMD5;
        SecureRandom mySecureRand = new SecureRandom();
        String s_id = String.valueOf(PubnubCore.class.hashCode());
        StringBuffer sbValueBeforeMD5 = new StringBuffer();
        try {
            long time = System.currentTimeMillis();
            long rand = 0;
            rand = mySecureRand.nextLong();
            sbValueBeforeMD5.append(s_id);
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(time));
            sbValueBeforeMD5.append(":");
            sbValueBeforeMD5.append(Long.toString(rand));
            valueBeforeMD5 = sbValueBeforeMD5.toString();
            byte[] array = PubnubCrypto.md5(valueBeforeMD5);
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < array.length; ++j) {
                int b = array[j] & 0xFF;
                if (b < 0x10) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(b));
            }
            valueAfterMD5 = sb.toString();
            String raw = valueAfterMD5.toUpperCase();
            sb = new StringBuffer();
            sb.append(raw.substring(0, 8));
            sb.append("-");
            sb.append(raw.substring(8, 12));
            sb.append("-");
            sb.append(raw.substring(12, 16));
            sb.append("-");
            sb.append(raw.substring(16, 20));
            sb.append("-");
            sb.append(raw.substring(20));
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * This method sets timeout value for subscribe/presence. Default value is
     * 310000 milliseconds i.e. 310 seconds
     *
     * @param timeout
     *            Timeout value in milliseconds for subscribe/presence
     */
    public void setSubscribeTimeout(int timeout) {
        super.setSubscribeTimeout(timeout);
    }

    /**
     * This method set timeout value for non subscribe operations like publish,
     * history, hereNow. Default value is 15000 milliseconds i.e. 15 seconds.
     *
     * @param timeout
     *            Timeout value in milliseconds for Non subscribe operations
     *            like publish, history, hereNow
     */
    public void setNonSubscribeTimeout(int timeout) {
        super.setNonSubscribeTimeout(timeout);
    }

    protected String getUserAgent() {
        return "Java/3.4";
    }

    /**
     * Send a message to a channel.
     *
     * @param args
     *            Hashtable containing channel name, message.
     * @param callback
     *            object of sub class of Callback class
     * @deprecated as of version 3.5.2 and will be removed with 3.6.0 . Methods
     *             accepting Hashtable as arguments have been deprecated.
     */
    @Deprecated
    @Override
    public void publish(Hashtable args, Callback callback) {
        super.publish(args, callback);
    }

    /**
     * Send a message to a channel.
     *
     * @param args
     *            Hashtable containing channel name, message, callback
     * @deprecated As of version 3.5.2 . Will be removed in 3.6.0 . Methods
     *             accepting Hashtable as arguments have been deprecated.
     *
     */
    @Deprecated
    @Override
    public void publish(Hashtable args) {
        super.publish(args);
    }

    /**
     *
     * Read DetailedHistory for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param start
     *            Start time
     * @param end
     *            End time
     * @param count
     *            Upper limit on number of messages to be returned
     * @param reverse
     *            True if messages need to be in reverse order
     * @param callback
     *            Callback
     * @deprecated as of version 3.5.2 and will be removed with 3.6.0 . Replaced
     *             by
     *             {@link #history(String channel, long start, long end, int count, boolean reverse, Callback callback)}
     */
    @Deprecated
    @Override
    public void detailedHistory(final String channel, long start, long end,
            int count, boolean reverse, final Callback callback) {
        super.detailedHistory(channel, start, end, count, reverse, callback);
    }

    /**
     *
     * Read History for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param start
     *            Start time
     * @param end
     *            End time
     * @param count
     *            Upper limit on number of messages to be returned
     * @param reverse
     *            True if messages need to be in reverse order
     * @param callback
     *            Callback
     */
    public void history(final String channel, long start, long end, int count,
            boolean reverse, final Callback callback) {
        super.detailedHistory(channel, start, end, count, reverse, callback);
    }

    /**
     *
     * Read DetailedHistory for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param start
     *            Start time
     * @param reverse
     *            True if messages need to be in reverse order
     * @param callback
     *            Callback
     * @deprecated as of version 3.5.2 and will be removed with 3.6.0 . Replaced
     *             by
     *             {@link #history(String channel, long start, boolean reverse, Callback callback)}
     */
    @Deprecated
    @Override
    public void detailedHistory(String channel, long start, boolean reverse,
            Callback callback) {
        super.detailedHistory(channel, start, reverse, callback);
    }

    /**
     *
     * Read history for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param start
     *            Start time
     * @param reverse
     *            True if messages need to be in reverse order
     * @param callback
     *            Callback
     */
    public void history(String channel, long start, boolean reverse,
            Callback callback) {
        super.detailedHistory(channel, start, reverse, callback);
    }

    /**
     *
     * Read DetailedHistory for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param start
     *            Start time
     * @param end
     *            End time
     * @param callback
     *            Callback
     * @deprecated as of version 3.5.2 and will be removed with 3.6.0 . Replaced
     *             by
     *             {@link #history(String channel, long start, long end, Callback callback)}
     */
    @Deprecated
    @Override
    public void detailedHistory(String channel, long start, long end,
            Callback callback) {
        super.detailedHistory(channel, start, end, callback);
    }

    /**
     *
     * Read History for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param start
     *            Start time
     * @param end
     *            End time
     * @param callback
     *            Callback
     */

    public void history(String channel, long start, long end, Callback callback) {
        super.detailedHistory(channel, start, end, callback);
    }

    /**
     *
     * Read DetailedHistory for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param start
     *            Start time
     * @param end
     *            End time
     * @param reverse
     *            True if messages need to be in reverse order
     * @param callback
     *            Callback
     * @deprecated as of version 3.5.2 and will be removed with 3.6.0 . Replaced
     *             by
     *             {@link #history(String channel, long start, long end, boolean reverse, Callback callback)}
     */
    @Deprecated
    @Override
    public void detailedHistory(String channel, long start, long end,
            boolean reverse, Callback callback) {
        super.detailedHistory(channel, start, end, reverse, callback);
    }

    /**
     *
     * Read History for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param start
     *            Start time
     * @param end
     *            End time
     * @param reverse
     *            True if messages need to be in reverse order
     * @param callback
     *            Callback
     */
    public void history(String channel, long start, long end, boolean reverse,
            Callback callback) {
        super.detailedHistory(channel, start, end, reverse, callback);
    }

    /**
     *
     * Read DetailedHistory for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param count
     *            Upper limit on number of messages to be returned
     * @param reverse
     *            True if messages need to be in reverse order
     * @param callback
     *            Callback
     * @deprecated as of version 3.5.2 and will be removed with 3.6.0 . Replaced
     *             by
     *             {@link #history(String channel, int count, boolean reverse, Callback callback)}
     */
    @Deprecated
    @Override
    public void detailedHistory(String channel, int count, boolean reverse,
            Callback callback) {
        super.detailedHistory(channel, count, reverse, callback);
    }

    /**
     *
     * Read History for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param count
     *            Upper limit on number of messages to be returned
     * @param reverse
     *            True if messages need to be in reverse order
     * @param callback
     *            Callback
     */
    public void history(String channel, int count, boolean reverse,
            Callback callback) {
        super.detailedHistory(channel, count, reverse, callback);
    }

    /**
     *
     * Read DetailedHistory for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param reverse
     *            True if messages need to be in reverse order
     * @param callback
     *            Callback
     * @deprecated as of version 3.5.2 and will be removed with 3.6.0 . Replaced
     *             by
     *             {@link #history(String channel, boolean reverse, Callback callback)}
     */
    @Deprecated
    public void detailedHistory(String channel, boolean reverse,
            Callback callback) {
        super.detailedHistory(channel, reverse, callback);
    }

    /**
     *
     * Read History for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param reverse
     *            True if messages need to be in reverse order
     * @param callback
     *            Callback
     */

    public void history(String channel, boolean reverse, Callback callback) {
        super.detailedHistory(channel, reverse, callback);
    }

    /**
     *
     * Read DetailedHistory for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param count
     *            Maximum number of messages
     * @param callback
     *            Callback object
     * @deprecated as of version 3.5.2 and will be removed with 3.6.0 . Replaced
     *             by
     *             {@link #history(String channel, int count, Callback callback)}
     */
    @Deprecated
    public void detailedHistory(String channel, int count, Callback callback) {
        super.detailedHistory(channel, count, callback);
    }

    /**
     *
     * Read History for a channel.
     *
     * @param channel
     *            Channel name for which detailed history is required
     * @param count
     *            Maximum number of messages
     * @param callback
     *            Callback object
     */
    @Override
    public void history(String channel, int count, Callback callback) {
        super.detailedHistory(channel, count, callback);
    }

    /**
     * Unsubscribe/Disconnect from channel.
     *
     * @param args
     *            Hashtable containing channel name.
     * @deprecated as of version 3.5.2 and will be removed with 3.6.0 . Methods
     *             accepting Hashtable as arguments have been deprecated.
     */
    @Deprecated
    public void unsubscribe(Hashtable args) {
        String[] channelList = (String[]) args.get("channels");
        if (channelList == null) {
            channelList = new String[] { (String) args.get("channel") };
        }
        unsubscribe(channelList);
    }

    /**
     *
     * Listen for a message on a channel.
     *
     * @param args
     *            Hashtable containing channel name
     * @param callback
     *            Callback
     * @exception PubnubException
     *                Throws PubnubException if Callback is null
     * @deprecated as of version 3.5.2 and will be removed with 3.6.0 .
     */
    @Deprecated
    public void subscribe(Hashtable args, Callback callback)
            throws PubnubException {
        args.put("callback", callback);
        super.subscribe(args);
    }

    /**
     *
     * Listen for a message on a channel.
     *
     * @param args
     *            Hashtable containing channel name, callback
     * @exception PubnubException
     *                Throws PubnubException if Callback is null
     * @deprecated as of version 3.5.2 and will be removed with 3.6.0 . Methods
     *             accepting Hashtable as arguments have been deprecated.
     */
    @Deprecated
    public void subscribe(Hashtable args) throws PubnubException {
        super.subscribe(args);
    }

    private String ulsSign(String key, String data) throws PubnubException {
        Mac sha256_HMAC;

        try {
        sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes(),
                "HmacSHA256");
        sha256_HMAC.init(secret_key);
        byte[] hmacData = sha256_HMAC.doFinal(data.getBytes("UTF-8"));
        return new String(Base64Encoder.encode(hmacData)).replace('+', '-')
                .replace('/', '_');
        } catch (InvalidKeyException e1) {
            throw new PubnubException(getErrorObject(PubnubError.PNERROBJ_5071_ULSSIGN_ERROR, "Invalid Key : " + e1.toString()));
        } catch (NoSuchAlgorithmException e1) {
            throw new PubnubException(getErrorObject(PubnubError.PNERROBJ_5072_ULSSIGN_ERROR, "Invalid Algorithm : " + e1.toString()));
        } catch (IllegalStateException e1) {
            throw new PubnubException(getErrorObject(PubnubError.PNERROBJ_5073_ULSSIGN_ERROR, "Invalid State : " + e1.toString()));
        } catch (UnsupportedEncodingException e1) {
            throw new PubnubException(getErrorObject(PubnubError.PNERROBJ_5074_ULSSIGN_ERROR, "Unsupported encoding : " + e1.toString()));
        }
    }

    /** Grant r/w access based on channel and auth key
     * @param channel
     * @param auth_key
     * @param read
     * @param write
     * @param callback
     */
    public void ulsGrant(final String channel, String auth_key, boolean read,
            boolean write, final Callback callback) {

        Hashtable parameters = hashtableClone(params);

        String r = (read) ? "1" : "0";
        String w = (write) ? "1" : "0";

        String signature = "0";

        int timestamp = (int) ((new Date().getTime()) / 1000);

        if (this.SECRET_KEY.length() == 0) {
            callback.errorCallback(channel,
                    PubnubError.PNERROBJ_5062_SECRET_KEY_MISSING);
            return;
        }

        String sign_input = this.SUBSCRIBE_KEY + "\n" + this.PUBLISH_KEY + "\n"
                + "grant" + "\n" + "auth=" + auth_key + "&" + "channel="
                + channel + "&" + "r=" + r + "&" + "timestamp=" + timestamp
                + "&" + "w=" + w;


        try {
            signature = ulsSign(this.SECRET_KEY, sign_input);
        } catch (PubnubException e1) {
            callback.errorCallback(channel,
                    e1.getPubnubError());
            return;
        }


        parameters.put("w", w);
        parameters.put("timestamp", String.valueOf(timestamp));
        parameters.put("signature", signature);
        parameters.put("r", r);
        parameters.put("channel", channel);
        parameters.put("auth", auth_key);

        String[] urlComponents = { getOrigin(), "v2", "grant", "sub-key",
                this.SUBSCRIBE_KEY };

        HttpRequest hreq = new HttpRequest(urlComponents, parameters,
                new ResponseHandler() {
            public void handleResponse(HttpRequest hreq, String response) {
                JSONObject jso;
                try {
                    jso = new JSONObject(response);
                } catch (JSONException e) {
                    handleError(hreq, PubnubError.getErrorObject(
                            PubnubError.PNERROBJ_5066_INVALID_JSON,
                            response));
                    return;
                }
                callback.successCallback(channel, jso);
            }

            public void handleError(HttpRequest hreq, PubnubError error) {
                callback.errorCallback(channel, error);
                return;
            }
        });

        _request(hreq, nonSubscribeManager);

    }

    /** ULS Audit
     * @param callback
     */
    public void ulsAudit(final Callback callback) {

        Hashtable parameters = hashtableClone(params);

        String signature = "0";

        int timestamp = (int) ((new Date().getTime()) / 1000);

        if (this.SECRET_KEY.length() == 0) {
            callback.errorCallback("",
                    PubnubError.PNERROBJ_5063_SECRET_KEY_MISSING);
            return;
        }

        String sign_input = this.SUBSCRIBE_KEY + "\n" + this.PUBLISH_KEY + "\n"
                + "audit" + "\n"
                + "timestamp=" + timestamp;


        try {
            signature = ulsSign(this.SECRET_KEY, sign_input);
        } catch (PubnubException e1) {
            callback.errorCallback("",
                    e1.getPubnubError());
            return;
        }

        parameters.put("timestamp", String.valueOf(timestamp));
        parameters.put("signature", signature);

        String[] urlComponents = { getOrigin(), "v2", "audit", "sub-key",
                this.SUBSCRIBE_KEY };

        HttpRequest hreq = new HttpRequest(urlComponents, parameters,
                new ResponseHandler() {
            public void handleResponse(HttpRequest hreq, String response) {
                JSONObject jso;
                try {
                    jso = new JSONObject(response);
                } catch (JSONException e) {
                    handleError(hreq, PubnubError.getErrorObject(
                            PubnubError.PNERROBJ_5067_INVALID_JSON,
                            response));
                    return;
                }
                callback.successCallback("", jso);
            }

            public void handleError(HttpRequest hreq, PubnubError error) {
                callback.errorCallback("", error);
                return;
            }
        });

        _request(hreq, nonSubscribeManager);

    }

    /** ULS audit by channel
     * @param channel
     * @param callback
     */
    public void ulsAudit(final String channel,
            final Callback callback) {

        Hashtable parameters = hashtableClone(params);

        String signature = "0";

        int timestamp = (int) ((new Date().getTime()) / 1000);

        if (this.SECRET_KEY.length() == 0) {
            callback.errorCallback(channel,
                    PubnubError.PNERROBJ_5064_SECRET_KEY_MISSING);
            return;
        }

        String sign_input = this.SUBSCRIBE_KEY + "\n" + this.PUBLISH_KEY + "\n"
                + "audit" + "\n" + "channel="
                + channel + "&" + "timestamp=" + timestamp;

        try {
            signature = ulsSign(this.SECRET_KEY, sign_input);
        } catch (PubnubException e1) {
            callback.errorCallback(channel,
                    e1.getPubnubError());
            return;
        }

        parameters.put("timestamp", String.valueOf(timestamp));
        parameters.put("signature", signature);
        parameters.put("channel", channel);

        String[] urlComponents = { getOrigin(), "v2", "audit", "sub-key",
                this.SUBSCRIBE_KEY };

        HttpRequest hreq = new HttpRequest(urlComponents, parameters,
                new ResponseHandler() {
            public void handleResponse(HttpRequest hreq, String response) {
                JSONObject jso;
                try {
                    jso = new JSONObject(response);
                } catch (JSONException e) {
                    handleError(hreq, PubnubError.getErrorObject(
                            PubnubError.PNERROBJ_5068_INVALID_JSON,
                            response));
                    return;
                }
                callback.successCallback(channel, jso);
            }

            public void handleError(HttpRequest hreq, PubnubError error) {
                callback.errorCallback(channel, error);
                return;
            }
        });

        _request(hreq, nonSubscribeManager);

    }

    /** ULS audit by channel and auth key
     * @param channel
     * @param auth_key
     * @param callback
     */
    public void ulsAudit(final String channel, String auth_key,
            final Callback callback) {

        Hashtable parameters = hashtableClone(params);

        String signature = "0";

        int timestamp = (int) ((new Date().getTime()) / 1000);

        if (this.SECRET_KEY.length() == 0) {
            callback.errorCallback(channel,
                    PubnubError.PNERROBJ_5065_SECRET_KEY_MISSING);
            return;
        }

        String sign_input = this.SUBSCRIBE_KEY + "\n" + this.PUBLISH_KEY + "\n"
                + "audit" + "\n" + "auth=" + auth_key + "&" + "channel="
                + channel + "&" + "timestamp=" + timestamp;


        try {
            signature = ulsSign(this.SECRET_KEY, sign_input);
        } catch (PubnubException e1) {
            callback.errorCallback(channel,
                    e1.getPubnubError());
            return;
        }

        parameters.put("timestamp", String.valueOf(timestamp));
        parameters.put("signature", signature);
        parameters.put("channel", channel);
        parameters.put("auth", auth_key);

        String[] urlComponents = { getOrigin(), "v2", "audit", "sub-key",
                this.SUBSCRIBE_KEY };

        HttpRequest hreq = new HttpRequest(urlComponents, parameters,
                new ResponseHandler() {
            public void handleResponse(HttpRequest hreq, String response) {
                JSONObject jso;
                try {
                    jso = new JSONObject(response);
                } catch (JSONException e) {
                    handleError(hreq, PubnubError.getErrorObject(
                            PubnubError.PNERROBJ_5069_INVALID_JSON,
                            response));
                    return;
                }
                callback.successCallback(channel, jso);
            }

            public void handleError(HttpRequest hreq, PubnubError error) {
                callback.errorCallback(channel, error);
                return;
            }
        });

        _request(hreq, nonSubscribeManager);

    }

    /** ULS revoke by channel and auth key
     * @param channel
     * @param auth_key
     * @param callback
     */
    public void ulsRevoke(String channel, String auth_key, Callback callback) {
        ulsGrant(channel, auth_key, false, false, callback);
    }

}
