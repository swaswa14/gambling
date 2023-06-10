import siteMetadata from "@/data/siteMetadata";
import {PageSEO} from "@/components/SEO";
import LogInForm from "@/components/forms/log-in-form";
import RegistrationForm from "@/components/forms/user-registration-form";

export default function RegisterPage(){
    return (
        <>
            <PageSEO title={siteMetadata.title} description={siteMetadata.description} />
            {siteMetadata.newsletter.provider && (
                <div className="flex items-center justify-center pt-4">
                    <RegistrationForm/>
                </div>
            )}
        </>
    )
}