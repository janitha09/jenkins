package jenkins.security;

import hudson.Extension;
import hudson.FilePath;
import hudson.model.AdministrativeMonitor;
import hudson.remoting.Callable;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.HttpResponse;
import org.kohsuke.stapler.HttpResponses;
import org.kohsuke.stapler.QueryParameter;

import javax.inject.Inject;
import java.io.IOException;

/**
 * Report any rejected {@link Callable}s and {@link FilePath} executions and allow
 * admins to whitelist them.
 *
 * @since 1.THU
 * @author Kohsuke Kawaguchi
 */
@Extension
public class AdminCallableMonitor extends AdministrativeMonitor {
    @Inject
    Jenkins jenkins;

    @Inject
    AdminCallableWhitelist whitelist;

    @Override
    public boolean isActivated() {
        return whitelist.hasRejection();
    }

    @Override
    public String getDisplayName() {
        return "Slave \u2192 Master Command Whitelisting";
    }

    // bind this to URL
    public AdminCallableWhitelist getWhitelist() {
        return whitelist;
    }

    /**
     * Depending on whether the user said "examin" or "dismiss", send him to the right place.
     */
    public HttpResponse doAct(@QueryParameter String dismiss) throws IOException {
        if(dismiss!=null) {
            disable(true);
            return HttpResponses.redirectViaContextPath("/manage");
        } else {
            return HttpResponses.redirectTo("whitelist/");
        }
    }
}