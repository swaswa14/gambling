import {PageSEO} from "@/components/SEO";
import siteMetadata from "@/data/siteMetadata";
import RegistrationForm from "@/components/forms/user-registration-form";
import ForgotPasswordForm from "@/components/forms/forgot-password-form";

export default function ForgetPasswordPage(){
    return (
        <>
            <PageSEO title={siteMetadata.title} description={siteMetadata.description} />
            {siteMetadata.newsletter.provider && (
                <div className="flex items-center justify-center pt-4">
                    <ForgotPasswordForm/>
                </div>
            )}
        </>
    )
}