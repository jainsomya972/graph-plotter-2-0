export async function evaluate(postfixTokens, from, to, step) {
    console.log("Evaluating expression with inputs: ", postfixTokens, from, to, step);
    const res = await fetch("/api/expression/evaluate", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ postfixExpression:  postfixTokens, xAxis: {from, to, step} }),
    });
    return await res.json();
}

export async function preEvaluate(expression) {
    const res = await fetch("/api/expression/pre-evaluate", {
    method: "POST",
    headers: { "Content-Type": "text/plain" },
    body: expression,
    });
    return await res.json();
}