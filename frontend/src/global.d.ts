export {};
declare global {
  interface Window {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    google: any;
    handleGoogleSignIn: (response: {
      credential: string;
      error: string;
    }) => void;
  }
}
