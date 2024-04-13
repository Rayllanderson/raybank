import Session from "@/types/Session";
import { getServerAuthSession } from "../[...nextauth]/options";

export async function GET() {
    const session: Session = await getServerAuthSession();
  
    if (session) {
  
      const refreshToken = session.refreshToken;
  
      var url = `${process.env.KEYCLOAK_LOGOUT_URL}?client_id=${process.env.KEYCLOAK_ID}&refresh_token=${refreshToken}`;
      try {
        const resp = await fetch(url, { method: "POST", headers: { 'Content-Type': "application/x-www-form-urlencoded", 'Authorization': 'Bearer ' + session.token } });
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