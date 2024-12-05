import Session from "@/types/Session";
import { getServerAuthSession } from "../[...nextauth]/options";

export async function GET() {
    const session: Session = await getServerAuthSession();
  
    if (session) {
  
      const refreshToken = session.refreshToken;
  
      var url = `${process.env.LOGOUT_URL}?client_id=${process.env.PROVIDER_CLIENT_ID}&refresh_token=${refreshToken}`;
      try {
        const resp = await fetch(url, { method: "GET"});
        if (!resp.ok) {
          throw new Error('failed to logout ' + resp.status)
        }
      } catch (err) {
        console.error(err);
        return new Response(null, { status: 500 });
      }
    }
    return new Response(null, { status: 200 });
  }