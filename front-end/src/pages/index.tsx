
import { Inter } from 'next/font/google'
import ResponsiveAppBar from "@modules/components/Layout";
import SignIn from "@modules/components/login-form";


const inter = Inter({ subsets: ['latin'] })

export default function Home() {

  return (
      <ResponsiveAppBar>
        <SignIn/>
      </ResponsiveAppBar>
  )
}
