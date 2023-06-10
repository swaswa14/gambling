
import {PageSEO} from "@/components/SEO";
import siteMetadata from "@/data/siteMetadata";

import LogInForm from "@/components/forms/log-in-form";

import { useRouter } from 'next/router'

export default function LoginPage() {
    const router = useRouter()
    return (
        <>
            <PageSEO title={siteMetadata.title} description={siteMetadata.description} />
            <div className="divide-y divide-gray-200 dark:divide-gray-700">
                <div className="space-y-2 pt-6 pb-8 md:space-y-5">
                    <h1 className="text-3xl font-extrabold leading-9 tracking-tight text-gray-900 dark:text-gray-100 sm:text-4xl sm:leading-10 md:text-6xl md:leading-14">
                        Company name
                    </h1>
                    <p className="text-lg leading-7 text-gray-500 dark:text-gray-400">
                       tagline mga potang na.
                    </p>
                </div>

            </div>
            {siteMetadata.newsletter.provider && (
                <div className="flex items-center justify-center pt-4">
                    <LogInForm onForgotPassword={()=> router.push("/forgot_password")} onSignUp={()=> router.push("/register")}/>
                </div>
            )}
        </>
    )
}
