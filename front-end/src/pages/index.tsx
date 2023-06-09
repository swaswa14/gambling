
import { Inter } from 'next/font/google'
import ResponsiveAppBar from "@modules/components/Layout";


const inter = Inter({ subsets: ['latin'] })

export default function Home() {

  return (
      <ResponsiveAppBar/>
  )
}
